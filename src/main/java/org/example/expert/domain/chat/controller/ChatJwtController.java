package org.example.expert.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.config.JwtUtil;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jwt")
public class ChatJwtController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @GetMapping("/{userId}")
    public String issue(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidRequestException("User not found"));

        String bearerToken = jwtUtil.createToken(
                user.getId(), user.getEmail(), user.getUserRole(), user.getNickname());

        return jwtUtil.substringToken(bearerToken);
    }
}
