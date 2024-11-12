package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeDao extends JpaRepository<Theme, Long> {
    boolean existsByName(String name);
}
