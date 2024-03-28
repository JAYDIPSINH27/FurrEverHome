package com.furreverhome.Furrever_Home.controller;

import com.furreverhome.Furrever_Home.dto.chat.ChatCredentialsResponse;
import com.furreverhome.Furrever_Home.repository.ChatService;
import com.furreverhome.Furrever_Home.services.chat.ChatServiceImpl;
import io.getstream.chat.java.exceptions.StreamException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Chat controller.
 */
@RestController
@RequestMapping("/api/chats/")
public class ChatController {


    private final ChatService chatService;

    /**
     * Instantiates a new Chat controller.
     *
     * @param chatService the chat service
     */
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * Start chat session chat credentials response.
     *
     * @param fromUserId the from user id
     * @param toUserId   the to user id
     * @return the chat credentials response
     * @throws StreamException the stream exception
     */
    @GetMapping("from/{fromUserId}/to/{toUserId}")
    public ChatCredentialsResponse startChatSession(@PathVariable long fromUserId, @PathVariable long toUserId) throws StreamException {
        return chatService.createChatSession(fromUserId, toUserId);
    }

    @GetMapping("history/{userId}")
    public ChatCredentialsResponse getShelterChats(@PathVariable long userId) {
        return chatService.getChatHistory(userId);
    }
}
