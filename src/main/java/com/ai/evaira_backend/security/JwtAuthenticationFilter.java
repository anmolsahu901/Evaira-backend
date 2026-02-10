package com.ai.evaira_backend.security;


import com.ai.evaira_backend.entity.User;
import com.ai.evaira_backend.repository.UserRepository;

import com.ai.evaira_backend.utility.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//
//            try {
//                String email = jwtUtil.extractUsername(token);
//                Long userId = jwtUtil.extractUserId(token);
//
//                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                    User user = userRepository.findById(userId)
//                            .orElse(null);
//
//                    if (user != null) {
//                        UsernamePasswordAuthenticationToken authToken =
//                                new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
//                        SecurityContextHolder.getContext().setAuthentication(authToken);
//                    }
//                }
//            } catch (Exception e) {
//                // Invalid token - continue
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.out.println("🔍 Request: " + request.getRequestURI());
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null ) {
            String token = authHeader;

            try {
                String email = jwtUtil.extractUsername(token);
                Long userId = jwtUtil.extractUserId(token);

                if (email != null && userId != null &&
                        SecurityContextHolder.getContext().getAuthentication() == null) {

                    // ✅ FIX: Verify user still exists in database
                    User user = userRepository.findById(userId).orElse(null);
                    if (user == null) {
                        System.out.println("⚠️  User not found in database. Token rejected.");
                        filterChain.doFilter(request, response);
                        return;
                    }

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userId,           // principal = userId
                                    null,
                                    Collections.emptyList()
                            );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                // Invalid token – ignore, request will be treated as unauthenticated
            }
        }

        filterChain.doFilter(request, response);
    }

}
