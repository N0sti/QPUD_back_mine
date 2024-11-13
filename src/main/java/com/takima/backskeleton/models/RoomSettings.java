package com.takima.backskeleton.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class RoomSettings {

    @Id
    private Long id;  // Assurez-vous que chaque RoomSettings ait un ID unique

    // Supprimer roomId et laisser room comme référence à l'entité Room
    @OneToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")  // Cette annotation mappera la colonne room_id
    private Room room;  // Représente la salle associée

    private Integer numQuestions;
    private Integer timeLimit;

    // Constructeur avec arguments
    public RoomSettings(Room room, Integer numQuestions, Integer timeLimit) {
        this.room = room;
        this.numQuestions = numQuestions;
        this.timeLimit = timeLimit;
    }

    // Constructeur par défaut
    public RoomSettings() {
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(Integer numQuestions) {
        this.numQuestions = numQuestions;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
