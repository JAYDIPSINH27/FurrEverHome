package com.furreverhome.Furrever_Home.unittests.services.userprofilemanagementservice;

import com.furreverhome.Furrever_Home.dto.profile.*;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.services.userprofilemanagementservice.UserProfileManagementService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserProfileManagementServiceTest {
    @Mock
    private PetAdopterRepository petAdopterRepository;

    @Mock
    private ShelterRepository shelterRepository;

    @Mock
    private UserProfileMapper userProfileMapper;

    @InjectMocks
    private UserProfileManagementService userProfileManagementService;

    /**
     * Test case to verify the successful update of a user profile when the user exists.
     */
    @Test
    void updateUserProfileWhenUserExists() {
        // Arrange
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
        PetAdopter petAdopter = new PetAdopter();
        PetAdopter updatedPetAdopter = new PetAdopter();
        UpdateUserProfileResponseDto expectedResponse = new UpdateUserProfileResponseDto(userId, "John", "Doe", "1234567890", "123 Main St", petAdopter.getCity(), petAdopter.getCountry(), petAdopter.getZipcode());

        when(petAdopterRepository.findByUserId(userId)).thenReturn(Optional.of(petAdopter));
        when(userProfileMapper.toPetAdopterEntity(requestDto, petAdopter)).thenReturn(updatedPetAdopter);
        when(petAdopterRepository.save(updatedPetAdopter)).thenReturn(updatedPetAdopter);
        when(userProfileMapper.toUpdateUserProfileResponseDto(updatedPetAdopter)).thenReturn(expectedResponse);

        // Act
        UpdateUserProfileResponseDto actualResponse = userProfileManagementService.updateUserProfile(userId, requestDto);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(petAdopterRepository).findByUserId(userId);
        verify(userProfileMapper).toPetAdopterEntity(requestDto, petAdopter);
        verify(petAdopterRepository).save(updatedPetAdopter);
        verify(userProfileMapper).toUpdateUserProfileResponseDto(updatedPetAdopter);
    }

    /**
     * Test case to verify the behavior when attempting to update a user profile when the user does not exist.
     * It ensures that an EntityNotFoundException is thrown.
     */
    @Test
    void updateUserProfileWhenUserDoesNotExist() {
        // Arrange
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

        when(petAdopterRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            userProfileManagementService.updateUserProfile(userId, requestDto);
        });

        verify(petAdopterRepository).findByUserId(userId);
        verify(userProfileMapper, never()).toPetAdopterEntity(any(), any());
        verify(petAdopterRepository, never()).save(any());
    }

    /**
     * Test case to verify the retrieval of a user profile when the user exists.
     */
    @Test
    void getUserProfileWhenUserExists() {
        // Arrange
        Long userId = 1L;
        PetAdopter petAdopter = new PetAdopter();
        UpdateUserProfileResponseDto expectedResponse = new UpdateUserProfileResponseDto(
            userId, // id
            "Jane",            // firstName
            "Doe",             // lastName
            "+1987654321",     // phoneNumber
            "789 Maple Avenue",// address
            "Anytown",         // city
            "Countryland",     // country
            "67890"            // zipcode
        );

        when(petAdopterRepository.findByUserId(userId)).thenReturn(Optional.of(petAdopter));
        when(userProfileMapper.toUpdateUserProfileResponseDto(petAdopter)).thenReturn(expectedResponse);

        // Act
        UpdateUserProfileResponseDto actualResponse = userProfileManagementService.getUserProfile(userId);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(petAdopterRepository).findByUserId(userId);
        verify(userProfileMapper).toUpdateUserProfileResponseDto(petAdopter);
    }

    /**
     * Test case to verify the behavior when attempting to retrieve a user profile when the user does not exist.
     * It ensures that an EntityNotFoundException is thrown.
     */
    @Test
    void getUserProfileWhenUserDoesNotExist() {
        // Arrange
        Long userId = 1L;

        when(petAdopterRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userProfileManagementService.getUserProfile(userId));

        verify(petAdopterRepository).findByUserId(userId);
    }

    /**
     * Test case to verify the successful update of a shelter profile when the shelter exists.
     */
    @Test
    void updateShelterProfileWhenShelterExists() {
        // Arrange
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
        Shelter shelter = new Shelter();
        Shelter updatedShelter = new Shelter();
        UpdateShelterProfileResponseDto expectedResponse = new UpdateShelterProfileResponseDto(
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

        when(shelterRepository.findByUserId(shelterId)).thenReturn(Optional.of(shelter));
        when(userProfileMapper.toShelterEntity(requestDto, shelter)).thenReturn(updatedShelter);
        when(shelterRepository.save(updatedShelter)).thenReturn(updatedShelter);
        when(userProfileMapper.toUpdateShelterProfileResponseDto(updatedShelter)).thenReturn(expectedResponse);

        // Act
        UpdateShelterProfileResponseDto actualResponse = userProfileManagementService.updateShelterProfile(shelterId, requestDto);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(shelterRepository).findByUserId(shelterId);
        verify(userProfileMapper).toShelterEntity(requestDto, shelter);
        verify(shelterRepository).save(updatedShelter);
        verify(userProfileMapper).toUpdateShelterProfileResponseDto(updatedShelter);
    }

    /**
     * Test updateShelterProfile method when the shelter does not exist.
     */
    @Test
    void updateShelterProfileWhenShelterDoesNotExist() {
        // Arrange
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

        when(shelterRepository.findByUserId(shelterId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userProfileManagementService.updateShelterProfile(shelterId, requestDto));

        verify(shelterRepository).findByUserId(shelterId);
        verify(userProfileMapper, never()).toShelterEntity(any(), any());
        verify(shelterRepository, never()).save(any());
    }

    /**
     * Test getShelterProfile method when the shelter exists.
     */
    @Test
    void getShelterProfileWhenShelterExists() {
        // Arrange
        Long shelterId = 1L;
        Shelter shelter = new Shelter();
        UpdateShelterProfileResponseDto expectedResponse = new UpdateShelterProfileResponseDto(
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

        when(shelterRepository.findByUserId(shelterId)).thenReturn(Optional.of(shelter));
        when(userProfileMapper.toUpdateShelterProfileResponseDto(shelter)).thenReturn(expectedResponse);

        // Act
        UpdateShelterProfileResponseDto actualResponse = userProfileManagementService.getShelterProfile(shelterId);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(shelterRepository).findByUserId(shelterId);
        verify(userProfileMapper).toUpdateShelterProfileResponseDto(shelter);
    }

    /**
     * Test getShelterProfile method when the shelter does not exist.
     */
    @Test
    void getShelterProfileWhenShelterDoesNotExist() {
        // Arrange
        Long shelterId = 1L;

        when(shelterRepository.findByUserId(shelterId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userProfileManagementService.getShelterProfile(shelterId));

        verify(shelterRepository).findByUserId(shelterId);
    }
}
