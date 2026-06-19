package org.example.expert.domain.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TypingDto {

    private Long roomId;
    private boolean typing;
    private Long userId;
    private String userName;
}
