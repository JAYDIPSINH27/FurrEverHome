package com.furreverhome.Furrever_Home.unittests.services.chat;

import com.furreverhome.Furrever_Home.dto.chat.ChatCredentialsResponse;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.enums.Role;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.chat.ChatProviderService;
import com.furreverhome.Furrever_Home.services.chat.ChatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatServiceImplTest {
    @Mock
    private ChatProviderService chatProviderService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PetAdopterRepository petAdopterRepository;

    @Mock
    private ShelterRepository shelterRepository;

    @InjectMocks
    private ChatServiceImpl chatService;

    private User petAdopterUser;
    private PetAdopter petAdopter;
    private User shelterUser;
    private Shelter shelter;

    @BeforeEach
    void setUp() {
        // Initialize User, PetAdopter, and Shelter objects
        petAdopterUser = new User();
        petAdopterUser.setId(1L);
        petAdopterUser.setEmail("adopter@example.com");
        petAdopterUser.setRole(Role.PETADOPTER);

        petAdopter = new PetAdopter();
        petAdopter.setId(1L);
        petAdopter.setUser(petAdopterUser);
        petAdopter.setFirstname("John");

        shelterUser = new User();
        shelterUser.setId(2L);
        shelterUser.setEmail("shelter@example.com");
        shelterUser.setRole(Role.SHELTER);

        shelter = new Shelter();
        shelter.setId(1L);
        shelter.setUser(shelterUser);
        shelter.setName("Happy Paws");
    }

    /**
     * Test case for creating a chat session from a Pet Adopter to a Shelter.
     * It verifies that a chat session is created successfully.
     */
    @Test
    void createChatSession_fromPetAdopterToShelter_createsSessionSuccessfully() throws Exception {
        // Mock the repository and service method responses
        when(userRepository.findById(petAdopterUser.getId())).thenReturn(Optional.of(petAdopterUser));
        when(userRepository.findById(shelterUser.getId())).thenReturn(Optional.of(shelterUser));
        when(petAdopterRepository.findByUserId(petAdopterUser.getId())).thenReturn(Optional.of(petAdopter));
        when(shelterRepository.findByUserId(shelterUser.getId())).thenReturn(Optional.of(shelter));

        doNothing().when(chatProviderService).createChatChannel(any(), any(), any());
        when(chatProviderService.getApiKey()).thenReturn("apiKey");
        when(chatProviderService.getToken(any(), any(), any())).thenReturn("token");

        // Call the method under test
        ChatCredentialsResponse response = chatService.createChatSession(petAdopterUser.getId(), shelterUser.getId());

        // Assert the response is as expected
        assertNotNull(response);
        assertEquals("apiKey", response.apiKey());
        assertEquals("token", response.token());

        // Verify interactions with mocks
        verify(petAdopterRepository).findByUserId(petAdopterUser.getId());
        verify(shelterRepository).findByUserId(shelterUser.getId());
        verify(chatProviderService).createChatChannel(any(PetAdopter.class), any(Shelter.class), anyString());
        verify(chatProviderService).getApiKey();
        verify(chatProviderService).getToken(anyString(), any(), any());
    }

    /**
     * Test case for creating a chat session from a Shelter to a Pet Adopter.
     * It verifies that a chat session is created successfully.
     */
    @Test
    void createChatSession_fromShelterToPetAdopter_createsSessionSuccessfully() throws Exception {
        // Mock the repository and service method responses
        when(userRepository.findById(petAdopterUser.getId())).thenReturn(Optional.of(petAdopterUser));
        when(userRepository.findById(shelterUser.getId())).thenReturn(Optional.of(shelterUser));
        when(petAdopterRepository.findByUserId(petAdopterUser.getId())).thenReturn(Optional.of(petAdopter));
        when(shelterRepository.findByUserId(shelterUser.getId())).thenReturn(Optional.of(shelter));

        doNothing().when(chatProviderService).createChatChannel(any(), any(), any());
        when(chatProviderService.getApiKey()).thenReturn("apiKey");
//        when(chatProviderService.getShelterChatUserId(anyLong())).thenReturn("shelterId");
        when(chatProviderService.getToken(any(), any(), any())).thenReturn("token");

        // Call the method under test
        ChatCredentialsResponse response = chatService.createChatSession(shelterUser.getId(), petAdopterUser.getId());

        // Assert the response is as expected
        assertNotNull(response);
        assertEquals("apiKey", response.apiKey());
        assertEquals("token", response.token());

        // Verify interactions with mocks
        verify(petAdopterRepository).findByUserId(petAdopterUser.getId());
        verify(shelterRepository).findByUserId(shelterUser.getId());
        verify(chatProviderService).createChatChannel(any(PetAdopter.class), any(Shelter.class), anyString());
        verify(chatProviderService).getApiKey();
        verify(chatProviderService).getToken(anyString(), any(), any());
    }

    /**
     * Test case for getting the chat history for a Pet Adopter.
     * It verifies that the chat history is retrieved successfully.
     */
    @Test
    void getPetChatHistory_withValidUserId_returnsChatHistorySuccessfully() throws Exception {
        // Assuming getChatHistory simply validates the user and returns a token
        when(userRepository.findById(petAdopterUser.getId())).thenReturn(Optional.of(petAdopterUser));
        when(petAdopterRepository.findByUserId(petAdopterUser.getId())).thenReturn(Optional.of(petAdopter));

        when(chatProviderService.getApiKey()).thenReturn("apiKey");
        when(chatProviderService.getToken(anyString(), any(), any())).thenReturn("token");

        // Call the method under test
        ChatCredentialsResponse response = chatService.getChatHistory(petAdopterUser.getId());

        // Assert the response is as expected
        assertNotNull(response);
        assertEquals("apiKey", response.apiKey());
        assertNull(response.channelId()); // Expected null channelId for chat history
        assertEquals("token", response.token());

        // Verify interactions with mocks
        verify(userRepository).findById(anyLong());
        verify(petAdopterRepository).findByUserId(petAdopterUser.getId());
        verify(chatProviderService, never()).createChatChannel(any(), any(), anyString());
        verify(chatProviderService).getApiKey();
        verify(chatProviderService).getToken(anyString(), any(), any());
    }

    /**
     * Test case for getting the chat history for a Shelter.
     * It verifies that the chat history is retrieved successfully.
     */
    @Test
    void getShelterChatHistory_withValidUserId_returnsChatHistorySuccessfully() throws Exception {
        when(userRepository.findById(shelterUser.getId())).thenReturn(Optional.of(shelterUser));
        when(shelterRepository.findByUserId(shelterUser.getId())).thenReturn(Optional.of(shelter));

        when(chatProviderService.getApiKey()).thenReturn("apiKey");
        when(chatProviderService.getToken(anyString(), any(), any())).thenReturn("token");

        // Call the method under test
        ChatCredentialsResponse response = chatService.getChatHistory(shelterUser.getId());

        // Assert the response is as expected
        assertNotNull(response);
        assertEquals("apiKey", response.apiKey());
        assertNull(response.channelId()); // Expected null channelId for chat history
        assertEquals("token", response.token());

        // Verify interactions with mocks
        verify(userRepository).findById(anyLong());
        verify(shelterRepository).findByUserId(shelterUser.getId());
        verify(chatProviderService, never()).createChatChannel(any(), any(), anyString());
        verify(chatProviderService).getApiKey();
        verify(chatProviderService).getToken(anyString(), any(), any());
    }

    /**
     * Test case for creating a chat session with a nonexistent user ID.
     * It verifies that a ResponseStatusException is thrown.
     */
    @Test
    void createChatSession_withNonexistentUserId_throwsResponseStatusException() {
        long nonexistentUserId = 999L;
        when(userRepository.findById(nonexistentUserId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> chatService.createChatSession(nonexistentUserId, petAdopterUser.getId()));
        assertThrows(ResponseStatusException.class, () -> chatService.createChatSession(petAdopterUser.getId(), nonexistentUserId));
    }

    /**
     * Test case for getting the chat history with a nonexistent user ID.
     * It verifies that a ResponseStatusException is thrown.
     */
    @Test
    void getChatHistory_withNonexistentUserId_throwsResponseStatusException() {
        long nonexistentUserId = 999L; // Assuming this ID doesn't exist
        when(userRepository.findById(nonexistentUserId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> chatService.getChatHistory(nonexistentUserId));
    }

}
