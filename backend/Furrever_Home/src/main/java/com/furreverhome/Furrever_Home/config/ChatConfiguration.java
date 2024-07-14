package com.furreverhome.Furrever_Home.config;

import com.furreverhome.Furrever_Home.repository.ChatService;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.chat.ChatProviderService;
import com.furreverhome.Furrever_Home.services.chat.ChatServiceImpl;
import com.furreverhome.Furrever_Home.services.chat.StreamChatProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for configuring chat-related beans.
 */
@Configuration
public class ChatConfiguration {

    /**
     * Bean definition for creating a chat provider service.
     * @return ChatProviderService instance, specifically a StreamChatProvider.
     */
    @Bean
    public ChatProviderService chatProviderService() {
        return new StreamChatProvider();
    }

    /**
     * Bean definition for creating a chat service.
     * @param chatProviderService The chat provider service implementation.
     * @param userRepository The repository for user-related operations.
     * @param petAdopterRepository The repository for pet adopter-related operations.
     * @param shelterRepository The repository for shelter-related operations.
     * @return ChatService instance, specifically a ChatServiceImpl.
     */
    @Bean
    public ChatService chatService(
            ChatProviderService chatProviderService,
            UserRepository userRepository,
            PetAdopterRepository petAdopterRepository,
            ShelterRepository shelterRepository) {
        return new ChatServiceImpl(chatProviderService, userRepository, petAdopterRepository, shelterRepository);
    }
}
