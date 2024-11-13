package com.takima.backskeleton.repository;

import com.takima.backskeleton.models.RoomSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomSettingsRepository extends JpaRepository<RoomSettings, Integer> {
}
