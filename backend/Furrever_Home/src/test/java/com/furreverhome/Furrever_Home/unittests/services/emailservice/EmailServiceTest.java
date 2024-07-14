package com.furreverhome.Furrever_Home.unittests.services.emailservice;

import com.furreverhome.Furrever_Home.services.emailservice.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests sending an email with a simple message.
     */
    @Test
    void testSendEmailWithSimpleMessageShouldSucceed() {
        // Arrange
        String to = "test@example.com";
        String subject = "Test Subject";
        String body = "Test body";

        // Act
        emailService.sendEmail(to, subject, body);

        // Assert
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

//    @Test
//    void sendEmail_withMimeMessage_shouldSucceed() throws Exception {
//        // Arrange
//        String to = "test@example.com";
//        String subject = "Test Subject";
//        String body = "<h1>Test</h1>";
//        boolean isHTML = true;
//
//        // Act
//        emailService.sendEmail(to, subject, body, isHTML);
//
//        // Assert
//        verify(mailSender, times(1)).send(any(MimeMessagePreparator.class));
//    }
}
