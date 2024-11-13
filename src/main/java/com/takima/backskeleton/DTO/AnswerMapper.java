package com.takima.backskeleton.DTO;

import com.takima.backskeleton.models.Answer;
import com.takima.backskeleton.models.Question;

public class AnswerMapper {

    public static Answer fromDto(AnswerDto answerDto, Long questionId) {
        Answer answer = new Answer(questionId, answerDto.isCorrect(), answerDto.getBody());
        return answer;
    }

}
