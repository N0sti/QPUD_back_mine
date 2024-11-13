package com.takima.backskeleton.models;

import java.io.Serializable;

public class RoomSettingsThemesId implements Serializable {
    private Long roomId;
    private Long themeId;

    // Default constructor
    public RoomSettingsThemesId() {}

    // Constructor with parameters
    public RoomSettingsThemesId(Long roomId, Long themeId) {
        this.roomId = roomId;
        this.themeId = themeId;
    }

    // Getters and setters
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    // Override equals() and hashCode() for composite key behavior
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomSettingsThemesId that = (RoomSettingsThemesId) o;
        return roomId.equals(that.roomId) && themeId.equals(that.themeId);
    }

    @Override
    public int hashCode() {
        return 31 * roomId.hashCode() + themeId.hashCode();
    }
}
