package com.takima.backskeleton.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "answers")
@Getter
@Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question; // Lié à une Question

    @Column(name = "body", nullable = false)
    private String body; // Le contenu de la proposition de réponse

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect; // Si la réponse est correcte ou non

    // Constructeur no-arg nécessaire pour JPA
    protected Answer() {
    }

    // Constructeur pour initialiser Answer avec Question, body, et isCorrect
    public Answer(Question question, Boolean isCorrect, String body) {
        this.question = question;
        this.body = body;
        this.isCorrect = isCorrect;
    }

    public Answer(String answerText, boolean b) {
    }

    // Méthode pour obtenir le texte de la réponse
    public String getText() {
        return body; // Retourne le corps de la réponse
    }

    // Méthode pour vérifier si la réponse est correcte
    public boolean isCorrect() {
        return isCorrect; // Retourne si la réponse est correcte
    }

    // Ajoutez une méthode setCorrect dans la classe Answer si elle n'existe pas déjà
    public void setCorrect(boolean correct) {
        this.isCorrect = correct;
    }

}
