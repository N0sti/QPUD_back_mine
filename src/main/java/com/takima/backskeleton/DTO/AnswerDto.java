package com.takima.backskeleton.DTO;

import com.takima.backskeleton.models.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {

    private String body;         // Le corps de la réponse
    private boolean isCorrect;    // Indicateur si la réponse est correcte ou non

    // Constructeur qui transforme un objet Answer en AnswerDto
    public AnswerDto(Answer answer) {
        if (answer != null) {
            this.body = answer.getBody();  // Récupère le corps de la réponse
            this.isCorrect = answer.isCorrect();  // Récupère l'indicateur de validité
        }
    }

    // La méthode toString() pour une représentation personnalisée de l'objet
    @Override
    public String toString() {
        return "AnswerDto{" +
                "body='" + body + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }

    // Ajout de la méthode getIsCorrect() explicite
    public Boolean getIsCorrect() {
        return isCorrect;
    }

    // Ajout de la méthode isCorrect() explicite
    public Boolean isCorrect() {
        return isCorrect;
    }

    // Méthode pour obtenir le texte de la réponse
    public String getText() {
        return body; // Retourne le corps de la réponse
    }
}
