package com.takima.backskeleton.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@IdClass(RoomSettingsThemesId.class)  // Link to the composite key class
public class RoomSettingsThemes {

    @Id
    private Long roomId;

    @Id
    private Long themeId;

    // Default constructor
    public RoomSettingsThemes() {}

    // Constructor with parameters
    public RoomSettingsThemes(Long roomId, Long themeId) {
        this.roomId = roomId;
        this.themeId = themeId;
    }
}
