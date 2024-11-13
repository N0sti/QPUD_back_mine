package com.takima.backskeleton.models;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class RoomSettingsTypesId implements Serializable {

    // Getters et Setters
    private Long roomId;
    private Long typeId;

    // Constructeur par défaut
    public RoomSettingsTypesId() {}

    // Constructeur avec paramètres
    public RoomSettingsTypesId(Long roomId, Long typeId) {
        this.roomId = roomId;
        this.typeId = typeId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    // Implémentation de equals() et hashCode() pour que la clé composite fonctionne correctement
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomSettingsTypesId that = (RoomSettingsTypesId) o;
        return Objects.equals(roomId, that.roomId) && Objects.equals(typeId, that.typeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, typeId);
    }
}
