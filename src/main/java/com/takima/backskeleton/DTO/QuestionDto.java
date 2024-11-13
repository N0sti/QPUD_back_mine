package com.takima.backskeleton.DTO;

import com.takima.backskeleton.models.Question;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class QuestionDto {

    private Long id;  // Ajout de l'id de la question
    private String body;
    private Long themeId;  // ID du thème associé à la question
    private Long typeId;   // ID du type de la question
    private List<AnswerDto> answers;  // Liste des réponses

    // Constructeur public
    public QuestionDto(Long id, String body, Long themeId, Long typeId, List<AnswerDto> answers) {
        this.id = id;
        this.body = body;
        this.themeId = themeId;
        this.typeId = typeId;
        this.answers = answers != null ? answers : List.of();  // Assurez-vous que la liste n'est jamais null
    }

    public QuestionDto(Question question) {
        this.id = question.getId();
        this.body = question.getBody();
        this.themeId = question.getTheme() != null ? question.getTheme().getId() : null;  // Récupère l'ID du thème
        this.typeId = question.getType() != null ? question.getType().getId() : null;  // Récupère l'ID du type de question
        // Mappe les réponses associées à la question vers des AnswerDto
        this.answers = question.getAnswers() != null ?
                question.getAnswers().stream().map(AnswerDto::new).collect(Collectors.toList()) : List.of();
    }

    // Méthode pour obtenir les réponses sous forme de liste
    public List<AnswerDto> getAnswers() {
        return answers != null ? answers : List.of();  // Si null, retourne une liste vide
    }

    // Méthode pour obtenir l'ID de la question
    public Long getId() {
        return id;
    }

    public QuestionDto getTheme() {
        return null;
    }

    public void setAnswers(List<AnswerDto> answerDtos) {
        this.answers = answerDtos != null ? answerDtos : List.of();  // Remplace avec la nouvelle liste ou une liste vide si null
    }


    // Il n'est pas nécessaire d'avoir une méthode getTheme() car vous travaillez avec themeId.
}
