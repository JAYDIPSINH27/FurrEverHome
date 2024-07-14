package com.furreverhome.Furrever_Home.services.chat;

import com.furreverhome.Furrever_Home.dto.chat.UserRoleEntities;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import io.getstream.chat.java.exceptions.StreamException;
import io.getstream.chat.java.models.Channel;
import io.getstream.chat.java.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

import static com.furreverhome.Furrever_Home.services.chat.ChatUtils.getAvatarUrl;

@RequiredArgsConstructor
public class StreamChatProvider implements ChatProviderService {

    private final String CHATPETUSERIDCONSTANT = "testpetuser1";
    private final String CHATSHELTERUSERIDCONSTANT = "testshelteruser1";

    @Value("${io.getstream.chat.apiKey}")
    private String apiKey;

    /**
     * Retrieves the Stream Chat API key.
     *
     * @return The Stream Chat API key.
     */
    @Override
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Creates a chat channel between a pet adopter and a shelter.
     *
     * @param petAdopter The pet adopter entity.
     * @param shelter    The shelter entity.
     * @param channelId  The unique ID for the chat channel.
     */
    @Override
    public void createChatChannel(PetAdopter petAdopter, Shelter shelter, String channelId) {
        // Upsert both users
        var petStreamUser = upsertUser(CHATPETUSERIDCONSTANT, petAdopter.getFirstname(),
                getAvatarUrl(petAdopter.getUser().getEmail()));
        var shelterStreamUser = upsertUser(CHATSHELTERUSERIDCONSTANT, shelter.getName(),
                getAvatarUrl(shelter.getUser().getEmail()));

        try {
            createStreamChannel(petStreamUser,
                    shelterStreamUser,
                    new UserRoleEntities(shelter, petAdopter),
                    channelId,
                    getAvatarUrl("furrever_" + channelId + "@furrever.ca"));
        } catch (StreamException e) {
            // Duplicate is a false positive so allow it.
            if (!e.getLocalizedMessage().contains("Duplicate")) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Adds a user to the chat provider.
     *
     * @param userId    The ID of the user.
     * @param username  The username of the user.
     * @param imageUrl  The URL of the user's avatar image.
     */
    @Override
    public void addUser(String userId, String username, String imageUrl) {
        upsertUser(userId, username, imageUrl);
    }

    /**
     * Creates a chat channel with specified users and channel details.
     *
     * @param fromStreamUser    The user initiating the chat.
     * @param toStreamUser      The user receiving the chat.
     * @param userRoleEntities  The entities representing user roles.
     * @param channelId         The unique ID for the chat channel.
     * @param imageUrl          The URL of the channel's image.
     * @throws StreamException If an error occurs while creating the channel.
     */
    private void createStreamChannel(
            User.UserRequestObject fromStreamUser,
            User.UserRequestObject toStreamUser,
            UserRoleEntities userRoleEntities,
            String channelId,
            String imageUrl
    ) throws StreamException {
        var fromChannelUser = Channel.ChannelMemberRequestObject.builder().user(fromStreamUser).build();
        var toChannelUser = Channel.ChannelMemberRequestObject.builder().user(toStreamUser).build();


        var channelData = Channel.ChannelRequestObject.builder()
                .createdBy(fromStreamUser)
                .additionalField("name", userRoleEntities.shelter().getName() + " :-: " + userRoleEntities.petAdopter().getFirstname())
                .additionalField("petadopter", userRoleEntities.petAdopter().getUser().getEmail())
                .additionalField("shelter", userRoleEntities.shelter().getUser().getEmail())
                .additionalField("image", imageUrl)
                .members(List.of(fromChannelUser, toChannelUser))
                .build();

        Channel.getOrCreate("messaging", channelId)
                .data(channelData)
                .request();
    }

    /**
     * Upserts a user in the chat provider.
     *
     * @param userId    The ID of the user.
     * @param username  The username of the user.
     * @param imageUrl  The URL of the user's avatar image.
     * @return The user request object.
     */
    public User.UserRequestObject upsertUser(String userId, String username, String imageUrl) {
        var streamUser = io.getstream.chat.java.models.User.UserRequestObject.builder()
                .id(userId)
                .name(username)
                .additionalField("image", imageUrl)
                .build();
        var usersUpsertRequest = io.getstream.chat.java.models.User.upsert();
        usersUpsertRequest.user(streamUser);
        return streamUser;
    }

    /**
     * Generates a token for user authentication.
     *
     * @param userId     The ID of the user.
     * @param expiresAt  The expiration date for the token.
     * @param issuedAt   The issue date for the token.
     * @return The generated token.
     */
    @Override
    public String getToken(String userId, Date expiresAt, Date issuedAt) {
        return User.createToken(userId, expiresAt, issuedAt);
    }
}
