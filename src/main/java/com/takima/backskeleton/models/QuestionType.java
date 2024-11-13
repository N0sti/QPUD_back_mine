package com.takima.backskeleton.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "question_type")
@Getter
@Setter
public class QuestionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    // Constructeur privé pour utiliser le builder
    public QuestionType(Builder builder) {
        this.id = builder.id;
        this.type = builder.type;
    }

    // Constructeur par défaut pour JPA
    public QuestionType() {}

    public QuestionType(String type) {
        this.type = type;
    }
    // Explicit getter method for type
    public String getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return "QuestionType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }

    public static class Builder {
        private Long id;
        private String type;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public QuestionType build() {
            return new QuestionType(this);
        }
    }
}
