package com.furreverhome.Furrever_Home.repository;

import com.furreverhome.Furrever_Home.dto.chat.ChatCredentialsResponse;
import io.getstream.chat.java.exceptions.StreamException;

/**
 * The interface Chat service.
 */
public interface ChatService {
    /**
     * Create chat session chat credentials response.
     *
     * @param fromUserId the from user id
     * @param toUserId   the to user id
     * @return the chat credentials response
     * @throws StreamException the stream exception
     */
    ChatCredentialsResponse createChatSession(long fromUserId, long toUserId) throws StreamException;

    /**
     * Create chat session history for user.
     *
     * @param userId   the user id
     * @return the chat credentials response
     */
    ChatCredentialsResponse getChatHistory(long userId) ;
}
