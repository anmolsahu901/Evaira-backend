package com.ai.evaira_backend.security;

import com.ai.evaira_backend.entity.User;
import com.ai.evaira_backend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SecurityUtil {

    private static UserRepository userRepository;

    public SecurityUtil(UserRepository userRepository) {
        SecurityUtil.userRepository = userRepository;
    }

    public static synchronized Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new IllegalStateException("No authenticated user found");
        }

        Object principal = auth.getPrincipal();

        // 1. Compatibility with custom JWT token (principal is Long userId)
        if (principal instanceof Long) {
            return (Long) principal;
        }

        // 2. Integration with Supabase JWT (principal is Jwt object)
        if (principal instanceof Jwt jwt) {
            String email = jwt.getClaimAsString("email");
            if (email == null) {
                email = jwt.getSubject(); // Fallback to subject (UUID) if email not present
            }

            if (email != null) {
                String finalEmail = email;
                User user = userRepository.findFirstByEmail(finalEmail)
                        .orElseGet(() -> {
                            User newUser = new User();
                            newUser.setEmail(finalEmail);
                            
                            // Try to retrieve name from standard claims
                            String name = jwt.getClaimAsString("name");
                            if (name == null) {
                                // Supabase user metadata is nested in user_metadata claim
                                Map<String, Object> userMetadata = jwt.getClaim("user_metadata");
                                if (userMetadata != null && userMetadata.get("full_name") instanceof String) {
                                    name = (String) userMetadata.get("full_name");
                                }
                            }
                            newUser.setName(name != null ? name : "User");
                            return userRepository.save(newUser);
                        });
                return user.getId();
            }
        }

        throw new IllegalStateException("Unsupported authentication principal type: " + (principal != null ? principal.getClass().getName() : "null"));
    }
}

