package com.furreverhome.Furrever_Home.services.emailservice;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private final JavaMailSender mailSender;

    /**
     * Sends a simple email message.
     *
     * @param to      The recipient's email address.
     * @param subject The subject of the email.
     * @param body    The body of the email.
     */
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("furreverhome13@gmail.com");
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    /**
     * Sends an email message with HTML content.
     *
     * @param to      The recipient's email address.
     * @param subject The subject of the email.
     * @param body    The body of the email (HTML content).
     * @param isHTML  Indicates whether the body contains HTML content.
     * @throws MessagingException If an error occurs while sending the email.
     */
    public void sendEmail(String to, String subject, String body, boolean isHTML)
        throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        message.setFrom("furreverhome13@gmail.com");
        helper.setSubject(subject);
        helper.setText(body, isHTML); // The 'true' flag indicates that this is HTML content
        mailSender.send(message);
    }
}
