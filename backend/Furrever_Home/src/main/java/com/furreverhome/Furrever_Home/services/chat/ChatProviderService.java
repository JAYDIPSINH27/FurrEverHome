package com.furreverhome.Furrever_Home.services.chat;

import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public interface ChatProviderService {

    String getApiKey();

    void createChatChannel(PetAdopter user1, Shelter user2, String channelId);

    void addUser(String userId, String username, String imageUrl);

    @NotNull
    String getToken(@NotNull String userId, Date expiresAt, Date issuedAt);
}
