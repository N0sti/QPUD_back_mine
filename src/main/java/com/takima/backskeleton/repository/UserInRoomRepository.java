package com.takima.backskeleton.repository;

import com.takima.backskeleton.models.UserInRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInRoomRepository extends JpaRepository<UserInRoom, Integer> {
}
