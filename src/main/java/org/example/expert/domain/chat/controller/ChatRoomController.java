package org.example.expert.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.chat.entity.ChatRoom;
import org.example.expert.domain.chat.repository.ChatRoomRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/rooms")
public class ChatRoomController {
    private final ChatRoomRepository chatRoomRepository;

    @GetMapping
    public List<ChatRoom> rooms() { return chatRoomRepository.findAll(); }

    @PostMapping
    public ChatRoom create(@RequestParam String name) {
        return chatRoomRepository.save(new ChatRoom(name));
    }
}
