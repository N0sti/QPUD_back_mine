package com.takima.backskeleton.DTO;

import com.takima.backskeleton.models.Answer;
import com.takima.backskeleton.models.Question;

public class AnswerMapper {

    public static Answer fromDto(AnswerDto answerDto, Question question) {
        Answer answer = new Answer(answerDto.getBody(), answerDto.isCorrect());
        answer.setQuestion(question);  // Associer la réponse à la question
        return answer;
    }

}
