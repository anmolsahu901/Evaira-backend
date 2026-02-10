package com.ai.evaira_backend.controller;


import com.ai.evaira_backend.dto.ProfileDto;

import com.ai.evaira_backend.entity.User;
import com.ai.evaira_backend.repository.UserRepository;
import com.ai.evaira_backend.service.EmailService;
import com.ai.evaira_backend.service.OtpService;

import com.ai.evaira_backend.service.UserProfileService;
import com.ai.evaira_backend.utility.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private EmailService emailService;
    @Autowired
    private OtpService otpService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    // DTOs for requests/responses
    public static class SendOtpRequest {
        public String email;
    }

    public static class VerifyOtpRequest {
        public String email;
        public String otp;
    }

    public static class AuthResponse {
        public String token;
        public boolean isNewUser;
        public Long userId;
        public String email;

        public AuthResponse(String token, boolean isNewUser, Long userId, String email) {
            this.token = token;
            this.isNewUser = isNewUser;
            this.userId = userId;
            this.email = email;
        }
    }

    @PostMapping("/send-otp")  // for login/register
    public ResponseEntity<String> sendOtp(@RequestBody SendOtpRequest request) {
        log.info("+++++++++ sendOtp: ");
        String otp = otpService.generateOtp(request.email);
        emailService.sendOtp(request.email, otp);
        return ResponseEntity.ok("OTP sent.");
    }

    @PostMapping("/verify-otp") // to verify OTP then check user exist or not
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request) {
        if (!otpService.validateOtp(request.email, request.otp)) {
            return ResponseEntity.badRequest().body("Invalid OTP.");
        }
        log.info("inside verify OTP");
        User user = userRepository.findByEmail(request.email).orElse(null);
        boolean isNew = false;

        if (user == null) {
            user = new User();
            user.setEmail(request.email);
            user = userRepository.save(user);
            isNew = true;
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail());;
        AuthResponse response = new AuthResponse(token, isNew, user.getId(), user.getEmail());
        log.info("otp verified and sending response :{} ", "new user: " + response.isNewUser + "  token: "+ response.token +"   email: "+response.email+ "  id : "+response.userId );
        return ResponseEntity.ok(response);
    }


}




