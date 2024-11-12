package com.takima.backskeleton.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@IdClass(RoomQuestionId.class)  // Use the composite key class here
public class RoomQuestion {

    // Getters and setters
    @Id
    private Long roomId;

    @Id
    private Long questionId;

    // Constructor
    public RoomQuestion(Long roomId, Long questionId) {
        this.roomId = roomId;
        this.questionId = questionId;
    }

    // Default constructor
    public RoomQuestion() {}

}
