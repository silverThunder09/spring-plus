package org.example.expert.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.chat.dto.ChatMessageResponse;
import org.example.expert.domain.chat.repository.ChatMessageRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/rooms")
public class ChatQueryController {
    private final ChatMessageRepository chatMessageRepository;

    @GetMapping("/{roomId}/messages")
    public List<ChatMessageResponse> messages(@PathVariable Long roomId, @RequestParam(defaultValue = "50") int size) {
        return chatMessageRepository.findByRoomIdOrderByCreatedAtAsc(roomId)
                .stream().map(ChatMessageResponse::new).toList();
    }
}
