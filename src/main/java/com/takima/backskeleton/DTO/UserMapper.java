package com.takima.backskeleton.DTO;

import com.takima.backskeleton.models.User;

public class UserMapper {

    public static User fromDto(UserDto userDto) {
        return new User(userDto.getUsername(), userDto.isAdmin());
    }

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .isAdmin(user.isAdmin())
                .build();
    }
}
