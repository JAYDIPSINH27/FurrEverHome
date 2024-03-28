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
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.furreverhome.Furrever_Home.services.chat.ChatUtils.getAvatarUrl;

/**
 * The type Chat service.
 */
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatProviderService chatProviderService;
    private final UserRepository userRepository;
    private final PetAdopterRepository petAdopterRepository;
    private final ShelterRepository shelterRepository;

    private final String CHATPETUSERIDCONSTANT = "testpetuser1";
    private final String CHATSHELTERUSERIDCONSTANT = "testshelteruser1";

    /**
     * Creates a chat session between two users.
     *
     * @param fromUserId The ID of the user initiating the chat session.
     * @param toUserId The ID of the user receiving the chat session.
     * @return The credentials for the chat session.
     */
    @Override
    public ChatCredentialsResponse createChatSession(long fromUserId, long toUserId) {
        // Validate users
        var fromUser = validateUserExists(fromUserId);
        var toUser = validateUserExists(toUserId);

        // Determine roles and get entities
        var entities = determineRolesAndGetEntities(fromUser, toUser);

        // Generate the unique channel ID
        String channelId = generateChannelId(entities.petAdopter().getUser().getEmail(), entities.shelter().getUser().getEmail());

        // Create a channel with both users
        chatProviderService.createChatChannel(entities.petAdopter(), entities.shelter(), channelId);

        // Generate token for the 'from' user to connect to the client-side
        return generateTokenForUser(fromUser, entities.petAdopter(), entities.shelter(), channelId);
    }

    /**
     * Retrieves the chat history for a user.
     *
     * @param userId The ID of the user.
     * @return The credentials for accessing the chat history.
     */
    @Override
    public ChatCredentialsResponse getChatHistory(long userId) {
        var user = validateUserExists(userId);
        var entities = determineRolesAndGetEntities(user);

        // Upsert both users
        if (entities.petAdopter() != null) {
            chatProviderService.addUser(
                    entities.petAdopter().getId().toString(),
                    entities.petAdopter().getFirstname(),
                    getAvatarUrl(entities.petAdopter().getUser().getEmail())
            );
        }
        if (entities.shelter() != null) {
            chatProviderService.addUser(
                    entities.shelter().getId().toString(),
                    entities.shelter().getName(),
                    getAvatarUrl(entities.shelter().getUser().getEmail())
            );
        }

        return generateTokenForUser(user, entities.petAdopter(), entities.shelter(), null);
    }

    /**
     * Generates a unique channel ID for a chat session based on user emails.
     *
     * @param petAdopterEmail The email of the pet adopter.
     * @param shelterEmail The email of the shelter.
     * @return The generated channel ID.
     */
    private String generateChannelId(String petAdopterEmail, String shelterEmail) {
        int maxLength = 14;
        String rawId = DigestUtils.sha256Hex(petAdopterEmail + shelterEmail);
        return rawId.substring(0, Math.min(rawId.length(), maxLength));
    }

    /**
     * Validates the existence of a user with the given ID.
     *
     * @param userId The ID of the user to validate.
     * @return The user if found.
     * @throws ResponseStatusException if the user is not found.
     */
    private User validateUserExists(long userId) throws ResponseStatusException {
        return userRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    /**
     * Determines the roles of the users involved in the chat session and retrieves their entities.
     *
     * @param fromUser The user initiating the chat session.
     * @param toUser The user receiving the chat session.
     * @return The entities of the users involved.
     */
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

    /**
     * Determines the role of the user and retrieves their entity.
     *
     * @param user The user for whom to determine the role.
     * @return The entity of the user.
     */
    private UserRoleEntities determineRolesAndGetEntities(User user) {
        Shelter shelter;
        PetAdopter petAdopter;

        if (user.getRole() == Role.PETADOPTER) {
            petAdopter = petAdopterRepository.findByUserId(user.getId()).get();
            shelter = null;
        } else {
            petAdopter = null;
            shelter = shelterRepository.findByUserId(user.getId()).get();
        }

        return new UserRoleEntities(shelter, petAdopter);
    }

    /**
     * Generates a chat token for a user and retrieves the credentials for accessing the chat.
     *
     * @param fromUser The user for whom to generate the token.
     * @param petAdopter The pet adopter entity.
     * @param shelter The shelter entity.
     * @param channelId The ID of the chat channel.
     * @return The credentials for accessing the chat.
     */
    private ChatCredentialsResponse generateTokenForUser(User fromUser, PetAdopter petAdopter, Shelter shelter, String channelId) {
        var calendar = new GregorianCalendar();
        calendar.add(Calendar.HOUR, 1);

        String token;
        String userId;
        String avatarUrl;

        if (fromUser.getRole() == Role.PETADOPTER) {
            userId = CHATPETUSERIDCONSTANT;
            avatarUrl = getAvatarUrl(petAdopter.getUser().getEmail());
            token = chatProviderService.getToken(userId, calendar.getTime(), null);
        } else {
            userId = CHATSHELTERUSERIDCONSTANT;
            avatarUrl = getAvatarUrl(shelter.getUser().getEmail());

            token = chatProviderService.getToken(userId, calendar.getTime(), null);
        }

        return new ChatCredentialsResponse(token, chatProviderService.getApiKey(), channelId, userId, avatarUrl);
    }
}
