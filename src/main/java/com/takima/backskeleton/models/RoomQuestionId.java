package com.takima.backskeleton.models;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class RoomQuestionId implements Serializable {
    // Getters and setters
    private Long roomId;
    private Long questionId;

    // Default constructor
    public RoomQuestionId() {}

    // Constructor with parameters
    public RoomQuestionId(Long roomId, Long questionId) {
        this.roomId = roomId;
        this.questionId = questionId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    // Override equals() and hashCode() for composite key behavior
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomQuestionId that = (RoomQuestionId) o;
        return roomId.equals(that.roomId) && questionId.equals(that.questionId);
    }

    @Override
    public int hashCode() {
        return 31 * roomId.hashCode() + questionId.hashCode();
    }
}
