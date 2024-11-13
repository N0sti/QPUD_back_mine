package com.takima.backskeleton.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Entity
public class UserInRoom implements Serializable {
    // Getters and setters
    private Long userId;
    private Long roomId;
    @Id
    private Long id;

    public UserInRoom() {}

    public UserInRoom(Long userId, Long roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    // hashCode and equals methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInRoom that = (UserInRoom) o;
        return userId.equals(that.userId) && roomId.equals(that.roomId);
    }

    @Override
    public int hashCode() {
        return 31 * userId.hashCode() + roomId.hashCode();
    }

    public void setId(Long id) {
        this.id = id;
    }

}
