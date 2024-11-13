package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.RoomDao;
import com.takima.backskeleton.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomDao roomDao;

    @Autowired
    public RoomService(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public List<Room> findAllRooms() {
        return roomDao.findAll();
    }

    public Room getRoomById(Long id) {
        return roomDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public void addRoom(Room room) {
        roomDao.save(room);
    }

    public void updateRoom(Long id, Room room) {
        Room existingRoom = roomDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Mettre à jour les propriétés de l'existant
        existingRoom.setName(room.getName());
        existingRoom.setCapacity(room.getCapacity());

        // Sauvegarder la salle mise à jour
        roomDao.save(existingRoom);
    }



    public void deleteRoom(Long id) {
        roomDao.deleteById(id);
    }
}
