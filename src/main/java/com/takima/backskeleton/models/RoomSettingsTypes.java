package com.takima.backskeleton.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import com.takima.backskeleton.models.RoomSettingsTypesId;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@IdClass(RoomSettingsTypesId.class)  // Indiquer que la clé composite est définie par RoomSettingsTypesId
public class RoomSettingsTypes {

    @Id
    private Long roomId;  // Partie de la clé composite

    @Id
    private Long typeId;  // Autre partie de la clé composite

    // Constructeur par défaut (JPA en a besoin)
    public RoomSettingsTypes() {}

    // Constructeur avec paramètres
    public RoomSettingsTypes(Long roomId, Long typeId) {
        this.roomId = roomId;
        this.typeId = typeId;
    }

}
