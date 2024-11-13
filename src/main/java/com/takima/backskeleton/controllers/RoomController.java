package com.takima.backskeleton.controllers;

import com.takima.backskeleton.DTO.RoomDto;
import com.takima.backskeleton.DTO.RoomMapper;
import com.takima.backskeleton.models.Room;
import com.takima.backskeleton.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // GET - Récupérer toutes les salles
    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        List<RoomDto> rooms = roomService.findAllRooms().stream()
                .map(RoomMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    // GET - Récupérer une salle par ID
    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long id) {
        RoomDto roomDto = RoomMapper.toDto(roomService.getRoomById(id));
        return new ResponseEntity<>(roomDto, HttpStatus.OK);
    }

    // POST - Créer une nouvelle salle
    @PostMapping
    public ResponseEntity<RoomDto> createRoom(@RequestBody RoomDto roomDto) {
        Room room = RoomMapper.fromDto(roomDto);
        roomService.addRoom(room);
        return new ResponseEntity<>(RoomMapper.toDto(room), HttpStatus.CREATED);
    }

    // PUT - Mettre à jour une salle existante
    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable Long id, @RequestBody RoomDto roomDto) {
        Room room = RoomMapper.fromDto(roomDto);
        roomService.updateRoom(id, room);
        return new ResponseEntity<>(RoomMapper.toDto(room), HttpStatus.OK);
    }

    // DELETE - Supprimer une salle par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
