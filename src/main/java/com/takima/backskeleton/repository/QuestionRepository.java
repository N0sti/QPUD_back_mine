package com.takima.backskeleton.repository;

import com.takima.backskeleton.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    // Utilisation de @Query pour filtrer les questions par thème
    @Query("SELECT q FROM Question q WHERE q.theme.name = :theme")
    List<Question> findByTheme(String theme);

}
