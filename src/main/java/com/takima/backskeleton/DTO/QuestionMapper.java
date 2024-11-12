package com.takima.backskeleton.DTO;

import com.takima.backskeleton.models.Answer;
import com.takima.backskeleton.models.Question;
import com.takima.backskeleton.models.QuestionType;
import com.takima.backskeleton.models.Theme;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionMapper {

    public static QuestionDto toDto(Question question) {
        if (question == null) {
            return null;
        }

        Long themeId = question.getTheme() != null ? question.getTheme().getId() : null;
        Long typeId = question.getQuestionType() != null ? question.getQuestionType().getId() : null;
        List<AnswerDto> answerDtos = question.getAnswers() != null ?
                question.getAnswers().stream()
                        .map(answer -> new AnswerDto(answer.getText(), answer.isCorrect()))
                        .collect(Collectors.toList()) : new ArrayList<>();

        return new QuestionDto(
                question.getId(),
                question.getBody(),
                themeId,  // Assurez-vous d'envoyer l'ID du thème
                typeId,   // Assurez-vous d'envoyer l'ID du type
                answerDtos
        );
    }


    public static Question fromDto(QuestionDto questionDto, Theme theme, QuestionType questionType) {
        if (questionDto == null) {
            return null;
        }

        // Convertir les réponses en objets Answer
        List<Answer> answers = questionDto.getAnswers().stream()
                .map(answerDto -> new Answer(answerDto.getText(), answerDto.isCorrect()))
                .collect(Collectors.toList());

        // Créer un nouvel objet Question avec les informations du DTO et les objets Theme et QuestionType
        Question question = new Question();
        question.setId(questionDto.getId());
        question.setBody(questionDto.getBody());
        question.setTheme(theme);            // Assurez-vous d'utiliser l'objet Theme passé en paramètre
        question.setQuestionType(questionType);  // Assurez-vous d'utiliser l'objet QuestionType passé en paramètre
        question.setAnswers(answers);        // Assigner les réponses à la question

        return question;
    }
}
