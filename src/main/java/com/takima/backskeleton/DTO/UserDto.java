package com.takima.backskeleton.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private String username;
    private boolean isAdmin;

    // Le constructeur est privé pour restreindre l'accès
    private UserDto(String username, boolean isAdmin) {
        this.username = username;
        this.isAdmin = isAdmin;
    }
}
