package com.ai.evaira_backend.scheduler;

import com.ai.evaira_backend.entity.User;
import com.ai.evaira_backend.repository.UserRepository;
import com.ai.evaira_backend.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CurationScheduler {

    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public CurationScheduler(UserRepository userRepository, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    // Runs at 9:00 AM, 12:00 PM (noon), and 7:00 PM (19:00) everyday
    @Scheduled(cron = "0 0 9,12,19 * * ?", zone = "Asia/Kolkata")
    // Runs at 11:30 PM (23:30) everyday
    @Scheduled(cron = "0 30 23 * * ?", zone = "Asia/Kolkata")
    public void sendDailyCurationAlerts() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            if (user.getPushToken() != null) {
                notificationService.sendPushNotification(
                        user.getPushToken(),
                        "Your AI stylist is waiting! ✨",
                        "Tap to see today's curated outfits recommended just for you.",
                        "curation"
                );
            }
        }
    }
}

