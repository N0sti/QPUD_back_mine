package com.takima.backskeleton.repository;

import com.takima.backskeleton.models.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionTypeRepository extends JpaRepository<QuestionType, Integer> {
}

