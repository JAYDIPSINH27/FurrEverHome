package com.furreverhome.Furrever_Home.unittests.controller;

import com.furreverhome.Furrever_Home.controller.ChatController;
import com.furreverhome.Furrever_Home.dto.chat.ChatCredentialsResponse;
import com.furreverhome.Furrever_Home.repository.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ChatControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ChatService chatService;

    @InjectMocks
    private ChatController chatController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(chatController)
                .build();
    }

    /**
     * Tests the {@link ChatController#startChatSession(long, long)} method.
     * Verifies that the controller returns a valid chat session response when starting a chat session.
     */
    @Test
    void testStartChatSession() throws Exception {
        long fromUserId = 1L;
        long toUserId = 2L;
        ChatCredentialsResponse mockResponse = new ChatCredentialsResponse("token",
                "apiKey", "channelId", "userChatId", "imageUrl");

        when(chatService.createChatSession(eq(fromUserId), eq(toUserId))).thenReturn(mockResponse);

        mockMvc.perform(get("/api/chats/from/{fromUserId}/to/{toUserId}", fromUserId, toUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apiKey").exists())
                .andExpect(jsonPath("$.channelId").exists())
                .andExpect(jsonPath("$.imageUrl").exists())
                .andExpect(jsonPath("$.userChatId").exists());

        verify(chatService).createChatSession(eq(fromUserId), eq(toUserId));
    }

    /**
     * Tests the getChatHistory method.
     * Verifies that the controller returns the chat history for a given user.
     */
    @Test
    void testGetChatHistory() throws Exception {
        long userId = 1L;
        ChatCredentialsResponse mockResponse = new ChatCredentialsResponse("token",
                "apiKey", null, "userChatId", "imageUrl");

        when(chatService.getChatHistory(eq(userId))).thenReturn(mockResponse);

        mockMvc.perform(get("/api/chats/history/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apiKey").exists())
                .andExpect(jsonPath("$.apiKey").exists())
                .andExpect(jsonPath("$.imageUrl").exists())
                .andExpect(jsonPath("$.userChatId").exists())
                .andExpect(jsonPath("$.channelId").value(mockResponse.channelId()));

        verify(chatService).getChatHistory(eq(userId));
    }

    /**
     * Tests the {@link ChatController#startChatSession(long, long)} method with an invalid user ID.
     * Verifies that the controller returns a bad request status when an invalid user ID is provided.
     */
    @Test
    void testStartChatSessionWithInvalidUserId() throws Exception {
        mockMvc.perform(get("/api/chats/from/{fromUserId}/to/{toUserId}", "invalid", 2L))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(chatService);
    }

    @Test
    void testGetChatHistoryWithInvalidUserId() throws Exception {
        mockMvc.perform(get("/api/chats/history/{userId}", "invalid"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(chatService);
    }
}
