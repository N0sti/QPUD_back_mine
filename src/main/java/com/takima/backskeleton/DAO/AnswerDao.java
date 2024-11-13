package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerDao extends JpaRepository<Answer, Long> {

    // Trouver toutes les réponses associées à une question
    List<Answer> findByQuestionId(Long questionId);

    // Trouver une réponse par son ID et l'ID de la question
    Answer findByQuestionIdAndId(Long questionId, Long id);

    // Optionnel : Supprimer toutes les réponses associées à une question (si nécessaire)
    void deleteByQuestionId(Long questionId);
}
