package com.takima.backskeleton.repository;

import com.takima.backskeleton.models.RoomSettingsTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomSettingsTypesRepository extends JpaRepository<RoomSettingsTypes, Integer> {
}
