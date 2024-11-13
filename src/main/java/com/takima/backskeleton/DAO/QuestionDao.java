package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Question;
import com.takima.backskeleton.models.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionDao extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE q.body = :body AND q.type = :type")
    Optional<Question> findByBodyAndType(String body, QuestionType type);
}
