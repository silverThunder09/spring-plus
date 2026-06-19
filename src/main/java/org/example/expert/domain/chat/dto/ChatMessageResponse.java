package org.example.expert.domain.chat.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.expert.domain.chat.entity.ChatMessage;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatMessageResponse {
    private Long messageId;
    private Long senderId;
    private String senderName;
    private String content;
    private LocalDateTime createdAt;

    public ChatMessageResponse(ChatMessage chatMessage) {
        this.messageId = chatMessage.getId();
        this.senderId = chatMessage.getSenderId();
        this.senderName = chatMessage.getSenderName();
        this.content = chatMessage.getContent();
        this.createdAt = chatMessage.getCreatedAt();
    }
}
