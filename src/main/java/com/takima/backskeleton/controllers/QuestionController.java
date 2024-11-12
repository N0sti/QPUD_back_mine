package com.takima.backskeleton.controllers;

import com.takima.backskeleton.DAO.QuestionTypeDao;
import com.takima.backskeleton.DAO.ThemeDao;
import com.takima.backskeleton.DTO.AnswerDto;
import com.takima.backskeleton.DTO.QuestionDto;
import com.takima.backskeleton.DTO.QuestionMapper;
import com.takima.backskeleton.models.Question;
import com.takima.backskeleton.models.QuestionType;
import com.takima.backskeleton.models.Theme;
import com.takima.backskeleton.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final ThemeDao themeDao;
    private final QuestionTypeDao questionTypeDao;

    // Liste toutes les questions
    @GetMapping("")
    public List<QuestionDto> listQuestions() {
        return questionService.findAll();
    }

    // Récupère une question par son ID
    @GetMapping("/{id}")
    public QuestionDto getQuestionById(@PathVariable Long id) {
        return questionService.getById(id);
    }

    // Ajoute une nouvelle question avec ses réponses
    @PostMapping("")
    public void addQuestion(@RequestBody QuestionDto questionDto) {
        // Récupérer le thème et le type de question à partir de leurs IDs dans le DTO
        Theme theme = themeDao.findById(questionDto.getThemeId())
                .orElseThrow(() -> new IllegalArgumentException("Theme not found"));
        QuestionType questionType = questionTypeDao.findById(questionDto.getTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Question type not found"));

        // Convertir le QuestionDto en un objet Question et ajouter les réponses à la question
        Question question = QuestionMapper.fromDto(questionDto, theme, questionType);

        // Ajouter la question (et ses réponses associées)
        questionService.addQuestion(questionDto, questionDto.getAnswers());
    }

    // Mettre à jour une question existante
    @PutMapping("/{id}")
    public void updateQuestion(@PathVariable Long id, @RequestBody QuestionDto questionDto) {
        // Mettre à jour la question en passant l'ID et le DTO
        questionService.updateQuestion(id, questionDto);
    }

    // Supprimer une question par son ID
    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
    }
}
