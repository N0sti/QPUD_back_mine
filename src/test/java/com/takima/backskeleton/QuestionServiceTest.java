package com.takima.backskeleton;

import com.takima.backskeleton.DAO.QuestionDao;
import com.takima.backskeleton.DAO.QuestionTypeDao;
import com.takima.backskeleton.DAO.ThemeDao;
import com.takima.backskeleton.DAO.AnswerDao;
import com.takima.backskeleton.DTO.AnswerDto;
import com.takima.backskeleton.DTO.QuestionDto;
import com.takima.backskeleton.models.Answer;
import com.takima.backskeleton.models.Question;
import com.takima.backskeleton.models.QuestionType;
import com.takima.backskeleton.models.Theme;
import com.takima.backskeleton.services.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceTest.class);

    @Mock private QuestionDao questionDao;
    @Mock private ThemeDao themeDao;
    @Mock private QuestionTypeDao questionTypeDao;
    @Mock private AnswerDao answerDao;

    @InjectMocks private QuestionService questionService;

    private QuestionDto questionDto;
    private List<AnswerDto> answers;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Log pour suivre la configuration des tests
        logger.info("Initializing tests...");

        // Créez la liste des réponses pour le test
        answers = new ArrayList<>();
        answers.add(new AnswerDto("Java is a programming language", true));
        answers.add(new AnswerDto("Java is a drink", false));

        // Créez un QuestionDto avec toutes les valeurs nécessaires, y compris la liste de réponses
        questionDto = new QuestionDto(1L, "What is Java?", 1L, 1L, answers);
        logger.info("Test setup completed: {}", questionDto);
    }

    @Test
    public void testAddQuestion() {
        logger.info("Running testAddQuestion...");

        // Simuler le retour des dépendances
        Theme theme = new Theme();
        QuestionType questionType = new QuestionType();
        when(themeDao.findById(anyLong())).thenReturn(Optional.of(theme));
        when(questionTypeDao.findById(anyLong())).thenReturn(Optional.of(questionType));
        when(questionDao.findByBodyAndType(anyString(), any())).thenReturn(Optional.empty());  // Pas de doublon

        logger.info("Mocks setup complete. Adding question...");

        // Appel du service
        questionService.addQuestion(questionDto, answers);

        // Vérification de l'enregistrement de la question et des réponses
        verify(questionDao, times(1)).save(any(Question.class));
        verify(answerDao, times(2)).save(any());

        logger.info("Test completed for testAddQuestion.");
    }

    @Test
    public void testAddQuestionWithDuplicateBodyAndType() {
        logger.info("Running testAddQuestionWithDuplicateBodyAndType...");

        // Créez un QuestionType simulé
        QuestionType questionType = new QuestionType();
        when(questionTypeDao.findById(anyLong())).thenReturn(Optional.of(questionType));

        // Simuler un doublon dans la base de données
        Question existingQuestion = new Question();
        when(questionDao.findByBodyAndType(anyString(), any())).thenReturn(Optional.of(existingQuestion));

        logger.info("Mocks setup complete. Testing for duplicate question...");

        // Vérification de l'exception levée
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            questionService.addQuestion(questionDto, answers);
        });

        // Vérification du message d'erreur
        assertEquals("A question with the same body and type already exists", exception.getMessage());

        logger.info("Test completed for testAddQuestionWithDuplicateBodyAndType.");
    }

    @Test
    public void testGetById() {
        logger.info("Running testGetById...");

        // Créez un Theme valide
        Theme theme = new Theme();
        theme.setId(1L);

        // Créez une Question valide avec un Theme
        Question question = new Question();
        question.setId(1L);
        question.setBody("What is Java?");
        question.setTheme(theme);

        // Créez une liste de réponses associées à la question
        Answer answer1 = new Answer("Java is a programming language", true);
        Answer answer2 = new Answer("Java is a drink", false);
        question.setAnswers(List.of(answer1, answer2));

        // Simulez la récupération de la question par ID
        when(questionDao.findById(1L)).thenReturn(Optional.of(question)); // Simulation correcte ici

        logger.info("Mocks setup complete. Fetching question by ID...");

        // Appeler la méthode getById du service
        QuestionDto result = questionService.getById(1L);

        // Vérification que le résultat n'est pas nul
        assertNotNull(result);

        // Vérification des propriétés de la question récupérée
        assertEquals(1L, result.getId());
        assertEquals("What is Java?", result.getBody());
        assertNotNull(result.getThemeId());
        assertEquals(1L, result.getThemeId());
        assertEquals(2, result.getAnswers().size());

        logger.info("Test completed for testGetById.");
    }


}
