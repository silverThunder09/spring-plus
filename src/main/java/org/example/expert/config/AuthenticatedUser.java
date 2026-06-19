package org.example.expert.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

@Getter
@RequiredArgsConstructor
public class AuthenticatedUser implements Principal {

    private final Long id;
    private final String nickname;

    @Override
    public String getName() {
        return nickname;
    }
}
