package org.example.expert.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserListResponse {

    private Long id;
    private String name;
    private String email;
}
