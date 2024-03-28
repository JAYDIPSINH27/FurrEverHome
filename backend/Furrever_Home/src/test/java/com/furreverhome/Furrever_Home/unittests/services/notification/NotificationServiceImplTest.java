package com.furreverhome.Furrever_Home.unittests.services.notification;

import com.furreverhome.Furrever_Home.PetDataGenerator;
import com.furreverhome.Furrever_Home.entities.PetVaccinationInfo;
import com.furreverhome.Furrever_Home.repository.PetVaccinationInfoRepository;
import com.furreverhome.Furrever_Home.services.emailservice.EmailService;
import com.furreverhome.Furrever_Home.services.notification.NotificationServiceImpl;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NotificationServiceImplTest {
    @Mock
    private PetVaccinationInfoRepository petVaccinationInfoRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case for sending vaccination reminders.
     * It verifies that emails are sent for pets with due vaccinations.
     */
    @Test
    public void testSendVaccinationReminders_sendsEmails() throws Exception {
        // Arrange
        LocalDate nextVaccinationDate = LocalDate.now().plusDays(7);

        List<PetVaccinationInfo> dueVaccinations = new ArrayList<>();
        dueVaccinations.add(PetDataGenerator.createMockPetVaccinationInfo(1L, "Dog", "Labrador", LocalDate.now().minusDays(30), nextVaccinationDate));
        dueVaccinations.add(PetDataGenerator.createMockPetVaccinationInfo(2L, "Dog", "Labrador", LocalDate.now().minusDays(30), nextVaccinationDate));
        when(petVaccinationInfoRepository.findAllWithNextVaccinationDueBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(dueVaccinations);
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString(), eq(false));

        // Act
        notificationService.sendVaccinationReminders();

        // Assert
        verify(emailService, times(dueVaccinations.size())).sendEmail(anyString(), anyString(), anyString(), eq(false));
    }

    /**
     * Test case for sending vaccination reminders when no vaccinations are due.
     * It verifies that no emails are sent.
     */
    @Test
    public void testSendVaccinationReminders_sendsNoEmailsWhenNoDueVaccinations() throws Exception {
        // Arrange
        when(petVaccinationInfoRepository.findAllWithNextVaccinationDueBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());

        // Act
        notificationService.sendVaccinationReminders();

        // Assert
        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString(), eq(false));
    }

    /**
     * Test case for handling errors during vaccination reminder emails sending.
     * It verifies that the service handles email sending errors gracefully.
     */
    @Test
    public void testSendVaccinationReminders_handlesEmailSendingErrors() throws Exception {
        // Arrange
        LocalDate nextVaccinationDate = LocalDate.now().plusDays(7);
        List<PetVaccinationInfo> dueVaccinations = new ArrayList<>();
        dueVaccinations.add(PetDataGenerator.createMockPetVaccinationInfo(1L, "Dog", "Labrador", LocalDate.now().minusDays(30), nextVaccinationDate));
        dueVaccinations.add(PetDataGenerator.createMockPetVaccinationInfo(2L, "Dog", "Labrador", LocalDate.now().minusDays(30), nextVaccinationDate));
        when(petVaccinationInfoRepository.findAllWithNextVaccinationDueBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(dueVaccinations);

        // Simulate email sending error for the first email attempt
        doThrow(MessagingException.class).when(emailService).sendEmail(anyString(), anyString(), anyString(), eq(false));

        // Act
        notificationService.sendVaccinationReminders();

        // Assert
        // Verify that sendEmail was attempted for each due vaccination
        verify(emailService, times(dueVaccinations.size())).sendEmail(anyString(), anyString(), anyString(), eq(false));
    }
}
