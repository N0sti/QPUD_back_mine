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

    // Constructeur pour initialiser Answer avec question_id, body, et isCorrect
    public Answer(Long questionId, Boolean isCorrect, String body) {
        this.question = new Question();
        this.question.setId(questionId);
        this.body = body;
        this.isCorrect = isCorrect;
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
    // Méthode toString pour afficher les détails de l'objet Answer
    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", questionId=" + (question != null ? question.getId() : "null") +
                ", body='" + body + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
