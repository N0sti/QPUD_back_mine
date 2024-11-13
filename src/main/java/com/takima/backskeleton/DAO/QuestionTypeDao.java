package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionTypeDao extends JpaRepository<QuestionType, Long> {
}
