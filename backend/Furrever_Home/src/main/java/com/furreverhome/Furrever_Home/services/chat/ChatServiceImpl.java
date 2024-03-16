package com.furreverhome.Furrever_Home.services.chat;

import com.furreverhome.Furrever_Home.dto.chat.ChatCredentialsResponse;
import com.furreverhome.Furrever_Home.dto.chat.UserRoleEntities;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.enums.Role;
import com.furreverhome.Furrever_Home.repository.ChatService;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.utils.jgravatar.Gravatar;
import com.furreverhome.Furrever_Home.utils.jgravatar.GravatarDefaultImage;
import com.furreverhome.Furrever_Home.utils.jgravatar.GravatarRating;
import io.getstream.chat.java.exceptions.StreamException;
import io.getstream.chat.java.models.Channel;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * The type Chat service.
 */
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    @Value("${io.getstream.chat.apiKey}")
    private String apiKey;

    private final UserRepository userRepository;
    private final PetAdopterRepository petAdopterRepository;
    private final ShelterRepository shelterRepository;


    @Override
    public ChatCredentialsResponse createChatSession(long fromUserId, long toUserId) throws StreamException {
        var calendar = new GregorianCalendar();
        calendar.add(Calendar.MINUTE, 60);

        // Validate users
        var fromUser = validateUserExists(fromUserId);
        var toUser = validateUserExists(toUserId);

        // Determine roles and get entities
        var entities = determineRolesAndGetEntities(fromUser, toUser);

        // Generate the unique channel ID
        String channelId = generateChannelId(entities.petAdopter().getUser().getEmail(), entities.shelter().getUser().getEmail());

        // Upsert both users
        var users = upsertUsers(entities.petAdopter(), entities.shelter());

        // Create a channel with both users
        createChannel(users.a, users.b, entities, channelId);

        // Generate token for the 'from' user to connect to the client-side
        return generateTokenForUser(fromUser, entities.petAdopter(), entities.shelter(), calendar, channelId);
    }

    @Override
    public String getAvatarUrl(String email) {
        Gravatar gravatar = new Gravatar();
        gravatar.setSize(50);
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        return gravatar.getUrl(email);
    }

    @Override
    public String generateChannelId(String petAdopterEmail, String shelterEmail) {
        String rawId = DigestUtils.sha256Hex(petAdopterEmail + shelterEmail);
        return rawId.substring(0, Math.min(rawId.length(), 14));
    }

    @Override
    public String getPetChatUserId(long userId) {
        return "testpetuser1";
    }

    @Override
    public String getShelterChatUserId(long userId) {
        return "testshelteruser1";
    }

    private User validateUserExists(long userId) throws ResponseStatusException {
        return userRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private UserRoleEntities determineRolesAndGetEntities(User fromUser, User toUser) {
        Shelter shelter;
        PetAdopter petAdopter;

        if (fromUser.getRole() == Role.PETADOPTER) {
            petAdopter = petAdopterRepository.findByUserId(fromUser.getId()).get();
            shelter = shelterRepository.findByUserId(toUser.getId()).get();
        } else {
            petAdopter = petAdopterRepository.findByUserId(toUser.getId()).get();
            shelter = shelterRepository.findByUserId(fromUser.getId()).get();
        }

        return new UserRoleEntities(shelter, petAdopter);
    }

    private Pair<io.getstream.chat.java.models.User.UserRequestObject,
            io.getstream.chat.java.models.User.UserRequestObject> upsertUsers(
            PetAdopter petAdopter,
            Shelter shelter) throws StreamException {
        var fromStreamUser = io.getstream.chat.java.models.User.UserRequestObject.builder()
                .id(getPetChatUserId(petAdopter.getId()))
                .name(petAdopter.getFirstname())
                .additionalField("image", getAvatarUrl(petAdopter.getUser().getEmail()))
                .build();
        var toStreamUser = io.getstream.chat.java.models.User.UserRequestObject.builder()
                .id(getShelterChatUserId(shelter.getId()))
                .name(shelter.getName())
                .additionalField("image", getAvatarUrl(shelter.getUser().getEmail()))
                .build();

        var usersUpsertRequest = io.getstream.chat.java.models.User.upsert();
        usersUpsertRequest.user(fromStreamUser);
        usersUpsertRequest.user(toStreamUser);
        usersUpsertRequest.request();
        return new Pair<>(fromStreamUser, toStreamUser);
    }

    private void createChannel(io.getstream.chat.java.models.User.UserRequestObject fromStreamUser, io.getstream.chat.java.models.User.UserRequestObject toStreamUser, UserRoleEntities userRoleEntities, String channelId) throws StreamException {
        var fromChannelUser = Channel.ChannelMemberRequestObject.builder().user(fromStreamUser).build();
        var toChannelUser = Channel.ChannelMemberRequestObject.builder().user(toStreamUser).build();


        var channelData = Channel.ChannelRequestObject.builder()
                .createdBy(fromStreamUser)
                .additionalField("name", userRoleEntities.shelter().getName() + " :-: " + userRoleEntities.petAdopter().getFirstname())
                .additionalField("petadopter", userRoleEntities.petAdopter().getUser().getEmail())
                .additionalField("shelter", userRoleEntities.shelter().getUser().getEmail())
                .additionalField("image", getAvatarUrl("furrever_" + channelId + "@furrever.ca"))
                .members(List.of(fromChannelUser, toChannelUser))
                .build();

        Channel.getOrCreate("messaging", channelId)
                .data(channelData)
                .request();
    }


    private ChatCredentialsResponse generateTokenForUser(User fromUser, PetAdopter petAdopter, Shelter shelter, GregorianCalendar calendar, String channelId) {
        String token;
        String userId;
        String avatarUrl;

        if (fromUser.getRole() == Role.PETADOPTER) {
            userId = getPetChatUserId(petAdopter.getId());
            avatarUrl = getAvatarUrl(petAdopter.getUser().getEmail());
            token = io.getstream.chat.java.models.User.createToken(userId, calendar.getTime(), null);
        } else {
            userId = getShelterChatUserId(shelter.getId());
            avatarUrl = getAvatarUrl(shelter.getUser().getEmail());
            token = io.getstream.chat.java.models.User.createToken(userId, calendar.getTime(), null);
        }

        return new ChatCredentialsResponse(token, apiKey, channelId, userId, avatarUrl);
    }
}