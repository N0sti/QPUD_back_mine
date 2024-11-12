package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.QuestionDao;
import com.takima.backskeleton.DAO.QuestionTypeDao;
import com.takima.backskeleton.DAO.ThemeDao;
import com.takima.backskeleton.DTO.AnswerDto;
import com.takima.backskeleton.DTO.QuestionDto;
import com.takima.backskeleton.DTO.QuestionMapper;
import com.takima.backskeleton.models.Answer;
import com.takima.backskeleton.models.Question;
import com.takima.backskeleton.models.QuestionType;
import com.takima.backskeleton.models.Theme;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.takima.backskeleton.DAO.AnswerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionDao questionDao;
    private final ThemeDao themeDao;
    private final QuestionTypeDao questionTypeDao;
    private final AnswerDao answerDao; // Ajout du DAO pour la gestion des réponses
    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);  // Déclaration du logger



    public List<QuestionDto> findAll() {
        return questionDao.findAll().stream()
                .map(QuestionMapper::toDto)
                .collect(Collectors.toList());
    }

    public QuestionDto getById(Long id) {
        logger.info("Fetching question with ID: " + id);  // Log avant d'aller chercher la question
        Optional<Question> optionalQuestion = questionDao.findById(id);
        if (optionalQuestion.isPresent()) {
            logger.info("Question found: " + optionalQuestion.get().getBody());  // Log si la question est trouvée
            Question question = optionalQuestion.get();
            return new QuestionDto(question);
        } else {
            logger.error("Question not found with ID: " + id);  // Log si la question n'est pas trouvée
            throw new EntityNotFoundException("Question not found with id: " + id);
        }
    }
    public void addQuestion(QuestionDto questionDto, List<AnswerDto> answers) {
        // Vérifier si la question existe déjà avec le même corps et type
        Optional<Question> existingQuestion = questionDao.findByBodyAndType(questionDto.getBody(), findQuestionTypeById(questionDto.getTypeId()));
        if (existingQuestion.isPresent()) {
            throw new IllegalArgumentException("A question with the same body and type already exists");
        }

        // Récupérer les entités Theme et QuestionType
        Theme theme = findThemeById(questionDto.getThemeId());
        QuestionType type = findQuestionTypeById(questionDto.getTypeId());

        // Créer la question
        Question question = new Question.Builder()
                .body(questionDto.getBody())
                .theme(theme)  // Associe le Theme à la Question
                .type(type)    // Associe le QuestionType à la Question
                .build();

        // Sauvegarder la question et l'associer aux entités Theme et QuestionType
        questionDao.save(question);

        // Sauvegarder les réponses associées
        if (answers != null) {
            for (AnswerDto answerDto : answers) {
                Answer answer = new Answer(answerDto.getBody(), answerDto.getIsCorrect());
                answer.setQuestion(question); // Associer la réponse à la question
                answerDao.save(answer); // Sauvegarder la réponse
            }
        }

        // Vérifiez que l'ID est bien généré
        if (question.getId() != null) {
            logger.info("La question a été sauvegardée avec l'ID : " + question.getId());
        } else {
            logger.error("L'ID de la question n'a pas été généré. Vérifiez la configuration de l'auto-incrémentation.");
        }
    }

    public Theme findThemeById(Long id) {
        return themeDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Theme not found"));
    }

    public QuestionType findQuestionTypeById(Long id) {
        return questionTypeDao.findById(id).orElseThrow(() -> new IllegalArgumentException("QuestionType not found"));
    }

    public void updateQuestion(Long id, QuestionDto questionDto) {
        Question question = questionDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Mettre à jour la question
        Theme theme = themeDao.findById(questionDto.getThemeId())
                .orElseThrow(() -> new RuntimeException("Theme not found"));
        QuestionType type = questionTypeDao.findById(questionDto.getTypeId())
                .orElseThrow(() -> new RuntimeException("Type not found"));

        question.setBody(questionDto.getBody());
        question.setTheme(theme);
        question.setType(type);

        // Sauvegarder la question mise à jour
        questionDao.save(question);

        // Mettre à jour les réponses
        if (questionDto.getAnswers() != null) {
            // Supprimer les anciennes réponses
            answerDao.deleteByQuestionId(id);

            // Ajouter les nouvelles réponses
            for (AnswerDto answerDto : questionDto.getAnswers()) {
                Answer answer = new Answer(answerDto.getBody(), answerDto.getIsCorrect());
                answer.setQuestion(question);
                answerDao.save(answer); // Sauvegarder la nouvelle réponse
            }
        }
    }

    public void deleteQuestion(Long id) {
        // Supprimer les réponses associées à la question
        answerDao.deleteByQuestionId(id);

        // Supprimer la question
        questionDao.deleteById(id);
    }
}
