package com.takima.backskeleton.repository;

import com.takima.backskeleton.models.RoomQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomQuestionRepository extends JpaRepository<RoomQuestion, Integer> {
}
