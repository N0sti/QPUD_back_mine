package com.takima.backskeleton.repository;

import com.takima.backskeleton.models.RoomSettingsThemes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomSettingsThemesRepository extends JpaRepository<RoomSettingsThemes, Integer> {

}
