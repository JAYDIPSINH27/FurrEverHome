package com.furreverhome.Furrever_Home.services.notification;

import com.furreverhome.Furrever_Home.entities.Pet;
import com.furreverhome.Furrever_Home.entities.PetVaccinationInfo;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.repository.PetVaccinationInfoRepository;
import com.furreverhome.Furrever_Home.services.emailservice.EmailService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class NotificationServiceImpl implements NotificationService {
    private final PetVaccinationInfoRepository petVaccinationInfoRepository;
    private final EmailService emailService;

    private final int notificationPeriod = 7;

    /**
     * Constructs a new instance of NotificationServiceImpl with the specified repositories.
     *
     * @param petVaccinationInfoRepository The repository for pet vaccination information.
     * @param emailService                 The service for sending email notifications.
     */
    public NotificationServiceImpl(PetVaccinationInfoRepository petVaccinationInfoRepository, EmailService emailService) {
        this.petVaccinationInfoRepository = petVaccinationInfoRepository;
        this.emailService = emailService;
    }

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    /**
     * Sends vaccination reminders for pets with upcoming vaccinations.
     * This method is scheduled to run daily at 12 AM.
     */
    @Transactional(readOnly = true)
//    @Scheduled(fixedRate = 10000)
    @Scheduled(cron = "0 0 0 * * ?") // This runs at 12 AM every day
    @Override
    public void sendVaccinationReminders() {

        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(notificationPeriod);

        List<PetVaccinationInfo> dueVaccinations = petVaccinationInfoRepository.findAllWithNextVaccinationDueBetween(today, nextWeek);

        dueVaccinations.forEach(vaccinationInfo -> {
            Pet pet = vaccinationInfo.getPet();
            Shelter shelter = pet.getShelter();
            String to = shelter.getUser().getEmail();
            String subject = "Vaccination Reminder";
            String body = String.format(
                    "Hi %s,%n%nThis is a reminder that your pet %s %s with ID: %s is due for their next vaccination on %s.",
                    shelter.getName(),
                    pet.getType(),
                    pet.getBreed().substring(0, 1).toUpperCase() + pet.getBreed().substring(1),
                    pet.getPetID(),
                    vaccinationInfo.getNextVaccinationDate().format(DateTimeFormatter.ofPattern("EEE MMM d, yyyy"))
            );
            try {
                emailService.sendEmail(to, subject, body, false); // Choose HTML or plaintext based on your preference
            } catch (MessagingException e) {
                log.error("Failed to send vaccination reminder email for pet: {}", pet.getPetID(), e);
            }
        });
    }
}
