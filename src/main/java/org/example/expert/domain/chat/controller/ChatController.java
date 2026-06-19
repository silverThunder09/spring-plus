package org.example.expert.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.config.AuthenticatedUser;
import org.example.expert.domain.chat.dto.ChatMessageDto;
import org.example.expert.domain.chat.dto.ChatMessageResponse;
import org.example.expert.domain.chat.dto.TypingDto;
import org.example.expert.domain.chat.entity.ChatMessage;
import org.example.expert.domain.chat.repository.ChatMessageRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")        // 프론트: /pub/chat.send
    public void send(ChatMessageDto dto, Principal principal) {
        AuthenticatedUser user = (AuthenticatedUser) principal;

        ChatMessage saved = chatMessageRepository.save(
                new ChatMessage(dto.getRoomId(), user.getId(), user.getNickname(), dto.getContent()));

        messagingTemplate.convertAndSend(
                "/sub/chat/" + dto.getRoomId(), new ChatMessageResponse(saved));
    }

    @MessageMapping("/chat.typing")      // 프론트: /pub/chat.typing
    public void typing(TypingDto dto, Principal principal) {
        AuthenticatedUser user = (AuthenticatedUser) principal;
        dto.setUserId(user.getId());
        dto.setUserName(user.getNickname());

        messagingTemplate.convertAndSend(
                "/sub/chat/" + dto.getRoomId() + "/typing", dto);
    }
}
