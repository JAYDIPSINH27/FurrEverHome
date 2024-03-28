package com.furreverhome.Furrever_Home.unittests.services.adminservices.impl;

import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.adminservices.impl.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {

    @Mock
    private ShelterRepository shelterRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test the {@code changeVerifiedStatus} method when approving.
     */
    @Test
    void testChangeVerifiedStatus_Approve() {
        // Arrange
        String email = "test@example.com";
        String status = "Approve";
        User user = new User();
        user.setId(1L);
        Shelter shelter = new Shelter();
        shelter.setUser(user);
        Optional<User> optionalUser = Optional.of(user);
        Optional<Shelter> optionalShelter = Optional.of(shelter);

        when(userRepository.findByEmail(email)).thenReturn(optionalUser);
        when(shelterRepository.findByUserId(user.getId())).thenReturn(optionalShelter);

        // Act
        boolean result = adminService.changeVerifiedStatus(email, status);

        // Assert
        assertTrue(result);
        assertTrue(shelter.isAccepted());
        assertFalse(shelter.isRejected());
        verify(shelterRepository, times(1)).save(shelter);
        verify(userRepository, times(1)).save(user);
    }

    /**
     * Test the {@code changeVerifiedStatus} method when rejecting.
     */
    @Test
    void testChangeVerifiedStatus_Reject() {
        // Arrange
        String email = "test@example.com";
        String status = "Reject";
        User user = new User();
        user.setId(1L);
        Shelter shelter = new Shelter();
        shelter.setUser(user);
        Optional<User> optionalUser = Optional.of(user);
        Optional<Shelter> optionalShelter = Optional.of(shelter);

        when(userRepository.findByEmail(email)).thenReturn(optionalUser);
        when(shelterRepository.findByUserId(user.getId())).thenReturn(optionalShelter);

        // Act
        boolean result = adminService.changeVerifiedStatus(email, status);

        // Assert
        assertTrue(result);
        assertFalse(shelter.isAccepted());
        assertTrue(shelter.isRejected());
        verify(shelterRepository, times(1)).save(shelter);
        verify(userRepository, times(1)).save(user);
    }

    /**
     * Test the {@code changeVerifiedStatus} method when the user is not found.
     */
    @Test
    void testChangeVerifiedStatus_UserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        String status = "Approve";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        boolean result = adminService.changeVerifiedStatus(email, status);

        // Assert
        assertFalse(result);
        verify(shelterRepository, never()).save(any());
        verify(userRepository, never()).save(any());
    }

    /**
     * Test the {@code changeVerifiedStatus} method when the shelter is not found.
     */
    @Test
    void testChangeVerifiedStatus_ShelterNotFound() {
        // Arrange
        String email = "test@example.com";
        String status = "Approve";
        User user = new User();
        user.setId(1L);
        Optional<User> optionalUser = Optional.of(user);

        when(userRepository.findByEmail(email)).thenReturn(optionalUser);
        when(shelterRepository.findByUserId(user.getId())).thenReturn(Optional.empty());

        // Act
        boolean result = adminService.changeVerifiedStatus(email, status);

        // Assert
        assertFalse(result);
        verify(shelterRepository, never()).save(any());
        verify(userRepository, never()).save(any());
    }
}
