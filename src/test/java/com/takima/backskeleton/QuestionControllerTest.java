package com.takima.backskeleton;

import com.takima.backskeleton.DAO.QuestionTypeDao;
import com.takima.backskeleton.DAO.ThemeDao;
import com.takima.backskeleton.DTO.AnswerDto;
import com.takima.backskeleton.DTO.QuestionDto;
import com.takima.backskeleton.controllers.QuestionController;
import com.takima.backskeleton.services.QuestionService;
import com.takima.backskeleton.models.Question;
import com.takima.backskeleton.models.Theme;
import com.takima.backskeleton.models.QuestionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionControllerTest {

    @InjectMocks
    private QuestionController questionController;

    @Mock
    private QuestionService questionService;

    @Mock
    private ThemeDao themeDao;

    @Mock
    private QuestionTypeDao questionTypeDao;

    @BeforeEach
    void setUp() {
        // Initialiser des objets mock pour les tests si nécessaire
    }

    @Test
    void testListQuestions() {
        // Given
        QuestionDto questionDto1 = new QuestionDto(1L, "Question 1", 1L, 1L, null);
        QuestionDto questionDto2 = new QuestionDto(2L, "Question 2", 1L, 1L, null);
        when(questionService.findAll()).thenReturn(Arrays.asList(questionDto1, questionDto2));

        // When
        var result = questionController.listQuestions();

        // Then
        assert(result.size() == 2);
        verify(questionService, times(1)).findAll();
    }

    @Test
    void testGetQuestionById() {
        // Given
        QuestionDto questionDto = new QuestionDto(1L, "Question 1", 1L, 1L, null);
        when(questionService.getById(1L)).thenReturn(questionDto);

        // When
        var result = questionController.getQuestionById(1L);

        // Then
        assert(result != null);
        assert(result.getId().equals(1L));
        verify(questionService, times(1)).getById(1L);
    }

    @Test
    void testAddQuestion() {
        // Given
        AnswerDto answerDto1 = new AnswerDto("Answer 1", true);
        AnswerDto answerDto2 = new AnswerDto("Answer 2", false);
        List<AnswerDto> answers = Arrays.asList(answerDto1, answerDto2);  // Crée une liste de réponses

        QuestionDto questionDto = new QuestionDto(1L, "New Question", 1L, 1L, answers);  // Ajoute la liste d'answers

        // Convertir le QuestionDto en un objet Question
        Theme theme = new Theme();  // Simuler un thème
        QuestionType questionType = new QuestionType();  // Simuler un type de question

        when(themeDao.findById(1L)).thenReturn(Optional.of(theme));
        when(questionTypeDao.findById(1L)).thenReturn(Optional.of(questionType));

        // When
        questionController.addQuestion(questionDto);

        // Then
        // Vérifier que la méthode addQuestion du service a été appelée avec un Question (converti) et une liste de réponses
        verify(questionService, times(1)).addQuestion(any(QuestionDto.class), eq(answers));  // Vérifie que la méthode est appelée avec le bon paramètre
    }




    @Test
    void testUpdateQuestion() {
        // Given
        QuestionDto questionDto = new QuestionDto(1L, "Updated Question", 1L, 1L, null);

        // When
        questionController.updateQuestion(1L, questionDto);

        // Then
        verify(questionService, times(1)).updateQuestion(1L, questionDto);
    }

    @Test
    void testDeleteQuestion() {
        // Given
        Long questionId = 1L;

        // When
        questionController.deleteQuestion(questionId);

        // Then
        verify(questionService, times(1)).deleteQuestion(questionId);
    }

    @Test
    void testAddQuestionWithThemeAndType() {
        // Given
        QuestionDto questionDto = new QuestionDto(1L, "Question with Theme and Type", 1L, 1L, null);
        Theme theme = new Theme();
        QuestionType type = new QuestionType();

        when(themeDao.findById(1L)).thenReturn(Optional.of(theme));
        when(questionTypeDao.findById(1L)).thenReturn(Optional.of(type));

        // When
        questionController.addQuestion(questionDto);

        // Then
        verify(questionService, times(1)).addQuestion(any(QuestionDto.class), any());
    }
}
