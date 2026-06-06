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

    // Runs every 5min
    @Scheduled(cron = "0 */5 * * * ?")
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

