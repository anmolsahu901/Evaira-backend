package com.ai.evaira_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

    private static final String EXPO_PUSH_URL = "https://exp.host/--/api/v2/push/send";
    private final RestTemplate restTemplate = new RestTemplate();

    public void sendPushNotification(String expoPushToken, String title, String body, String screen) {
        if (expoPushToken == null || expoPushToken.isEmpty()) {
            return;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> payload = new HashMap<>();
            payload.put("to", expoPushToken);
            payload.put("title", title);
            payload.put("body", body);
            payload.put("sound", "default");
            payload.put("channelId", "default");

            Map<String, String> data = new HashMap<>();
            data.put("screen", screen);
            payload.put("data", data);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            String response = restTemplate.postForObject(
                    EXPO_PUSH_URL,
                    request,
                    String.class);

            System.out.println("[notifications] Expo Response: " + response);
        } catch (Exception e) {
            System.err.println("[notifications] Failed to send push notification to Expo: " + e.getMessage());
        }
    }
}
