package com.furreverhome.Furrever_Home.unittests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furreverhome.Furrever_Home.controller.UserProfileManagementController;
import com.furreverhome.Furrever_Home.dto.profile.UpdateShelterProfileRequestDto;
import com.furreverhome.Furrever_Home.dto.profile.UpdateShelterProfileResponseDto;
import com.furreverhome.Furrever_Home.dto.profile.UpdateUserProfileRequestDto;
import com.furreverhome.Furrever_Home.dto.profile.UpdateUserProfileResponseDto;
import com.furreverhome.Furrever_Home.exception.GlobalExceptionHandler;
import com.furreverhome.Furrever_Home.services.userprofilemanagementservice.UserProfileManagementService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class UserProfileManagementControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private UserProfileManagementService userProfileManagementService;

    @InjectMocks
    private UserProfileManagementController userProfileManagementController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userProfileManagementController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    /**
     * Test case to verify the successful update of a user profile.
     * Verifies that the user profile is successfully updated and returns a 200 OK response with the updated profile details.
     */
    @Test
    void testUpdateUserProfile() throws Exception {
        Long userId = 1L;
        UpdateUserProfileRequestDto requestDto = new UpdateUserProfileRequestDto(
            "John",            // firstName
            "Doe",             // lastName
            "+123456789",      // phoneNumber
            "456 Elm Street",  // address
            "Springfield",     // city
            "USA",             // country
            "12345"            // zipcode
        );
        UpdateUserProfileResponseDto responseDto = new UpdateUserProfileResponseDto(
            userId, // id
            "Jane",            // firstName
            "Doe",             // lastName
            "+1987654321",     // phoneNumber
            "789 Maple Avenue",// address
            "Anytown",         // city
            "Countryland",     // country
            "67890"            // zipcode
        );

        when(userProfileManagementService.updateUserProfile(anyLong(), any(UpdateUserProfileRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(userProfileManagementService).updateUserProfile(anyLong(), any(UpdateUserProfileRequestDto.class));
    }

    /**
     * Test case to verify the successful retrieval of a user profile.
     * Verifies that the user profile is successfully retrieved and returns a 200 OK response with the profile details.
     */
    @Test
    void testGetUserProfileSuccess() throws Exception {
        Long userId = 1L;
        UpdateUserProfileResponseDto responseDto = new UpdateUserProfileResponseDto(
            userId, // id
            "Jane",            // firstName
            "Doe",             // lastName
            "+1987654321",     // phoneNumber
            "789 Maple Avenue",// address
            "Anytown",         // city
            "Countryland",     // country
            "67890"            // zipcode
        );

        given(userProfileManagementService.getUserProfile(eq(userId))).willReturn(responseDto);

        mockMvc.perform(get("/api/users/{userId}", userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(userProfileManagementService).getUserProfile(eq(userId));
    }

    /**
     * Test case to verify the handling of a user profile not found scenario.
     * Verifies that an appropriate response with status 404 is returned when the user profile is not found.
     */
    @Test
    void testGetUserProfileNotFound() throws Exception {
        Long userId = 2L;
        given(userProfileManagementService.getUserProfile(eq(userId))).willThrow(new EntityNotFoundException("User not found"));

        mockMvc.perform(get("/api/users/{userId}", userId))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(userProfileManagementService).getUserProfile(eq(userId));
    }

    /**
     * Test case to verify the successful update of a shelter profile.
     * Verifies that the shelter profile is successfully updated and returns a 200 OK response with the updated profile details.
     */
    @Test
    void testUpdateShelterProfileSuccess() throws Exception {
        Long shelterId = 1L;
        UpdateShelterProfileRequestDto requestDto = new UpdateShelterProfileRequestDto(
            "Happy Paws Shelter", // name
            "1234 Bark St",       // address
            "Puptown",            // city
            "Furdia",             // country
            "PAW123",             // zipcode
            100L,                 // capacity (Long value, hence the 'L' suffix)
            "+1234567890",        // contact
            "image", // imageBase64 (example base64 string)
            "SHL-7890123"         // license
        );
        UpdateShelterProfileResponseDto responseDto = new UpdateShelterProfileResponseDto(
            shelterId, // id
            "Happy Paws Shelter", // name
            "1234 Bark St", // address
            "Puptown", // city
            "PAW123", // zipcode
            100L, // capacity
            "+1234567890", // contact
            "image", // imageBase64
            "SHL-7890123", // license
            "Furdia" // country
        );

        given(userProfileManagementService.updateShelterProfile(eq(shelterId), any(UpdateShelterProfileRequestDto.class))).willReturn(responseDto);

        mockMvc.perform(put("/api/shelters/{shelterId}", shelterId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Happy Paws Shelter"))
                .andExpect(jsonPath("$.address").value("1234 Bark St"));

        verify(userProfileManagementService).updateShelterProfile(eq(shelterId), any(UpdateShelterProfileRequestDto.class));
    }

    /**
     * Test case to verify the handling of a shelter profile not found scenario.
     * Verifies that an appropriate response with status 404 is returned when the shelter profile is not found.
     */
    @Test
    void testUpdateShelterProfileNotFound() throws Exception {
        Long shelterId = 2L;
        UpdateShelterProfileRequestDto requestDto = new UpdateShelterProfileRequestDto(
            "Happy Paws Shelter", // name
            "1234 Bark St",       // address
            "Puptown",            // city
            "Furdia",             // country
            "PAW123",             // zipcode
            100L,                 // capacity (Long value, hence the 'L' suffix)
            "+1234567890",        // contact
            "image", // imageBase64 (example base64 string)
            "SHL-7890123"         // license
        );

        given(userProfileManagementService.updateShelterProfile(eq(shelterId), any(UpdateShelterProfileRequestDto.class))).willThrow(new EntityNotFoundException("Shelter not found"));

        mockMvc.perform(put("/api/shelters/{shelterId}", shelterId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(userProfileManagementService).updateShelterProfile(eq(shelterId), any(UpdateShelterProfileRequestDto.class));
    }

    /**
     * Test case to verify the handling of unauthorized user profile updates.
     * Verifies that an appropriate response with status 500 (Internal Server Error) is returned when the update request is unauthorized.
     */
    @Test
    void testUpdateUserProfileUnauthorized() throws Exception {
        Long userId = 1L;
        UpdateUserProfileRequestDto requestDto = new UpdateUserProfileRequestDto(
            "John",            // firstName
            "Doe",             // lastName
            "+123456789",      // phoneNumber
            "456 Elm Street",  // address
            "Springfield",     // city
            "USA",             // country
            "12345"            // zipcode
        );

        given(userProfileManagementService.updateUserProfile(eq(userId), any(UpdateUserProfileRequestDto.class))).willThrow(new AccessDeniedException("Unauthorized"));

        mockMvc.perform(put("/api/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        verify(userProfileManagementService).updateUserProfile(eq(userId), any(UpdateUserProfileRequestDto.class));
    }
}
