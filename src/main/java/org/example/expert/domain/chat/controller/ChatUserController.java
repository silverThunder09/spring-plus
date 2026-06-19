package org.example.expert.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.chat.dto.UserListResponse;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class ChatUserController {

    private final UserRepository userRepository;

    @GetMapping
    public List<UserListResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserListResponse(user.getId(), user.getNickname(), user.getEmail()))
                .toList();
    }
}
