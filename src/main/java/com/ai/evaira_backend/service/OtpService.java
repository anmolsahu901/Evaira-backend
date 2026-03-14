package com.ai.evaira_backend.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Random;

@Service
public class OtpService {
    private Map<String, String> otpStore = new ConcurrentHashMap<>();
    private final Random random = new Random();

    private static final Logger log = LoggerFactory.getLogger(OtpService.class);


    public String generateOtp(String email) {
        log.info("++++++++ generateOtp");
        String otp = String.format("%06d", random.nextInt(999999));
        otpStore.put(email, otp);
        return otp;
    }

    public boolean validateOtp(String email, String otp) {
        String validOtp = otpStore.get(email);
        if (validOtp != null && validOtp.equals(otp)) {
            otpStore.remove(email);
            return true;
        }
        return false;
    }
}
