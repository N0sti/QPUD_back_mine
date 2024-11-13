package com.takima.backskeleton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.takima.backskeleton.DTO.RoomDto;
import com.takima.backskeleton.controllers.RoomController;
import com.takima.backskeleton.models.Room;
import com.takima.backskeleton.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @Autowired
    private ObjectMapper objectMapper;

    private RoomDto roomDto;

    @BeforeEach
    void setUp() {
        roomDto = new RoomDto(1L, "Conference Room", 50);
    }

    // Test GET - Récupération de toutes les salles
    @Test
    void shouldReturnAllRooms() throws Exception {
        when(roomService.findAllRooms()).thenReturn(Arrays.asList(
                new Room("Meeting Room", 20),
                new Room("Conference Room", 50)
        ));

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Meeting Room"))
                .andExpect(jsonPath("$[1].capacity").value(50));

        verify(roomService, times(1)).findAllRooms();
    }

    // Test GET - Récupération d'une salle par ID
    @Test
    void shouldReturnRoomById() throws Exception {
        when(roomService.getRoomById(1L)).thenReturn(new Room("Conference Room", 50));

        mockMvc.perform(get("/api/rooms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Conference Room"))
                .andExpect(jsonPath("$.capacity").value(50));

        verify(roomService, times(1)).getRoomById(1L);
    }

    // Test POST - Création d'une nouvelle salle
    @Test
    void shouldCreateNewRoom() throws Exception {
        Room room = new Room("Conference Room", 50);
        // Simule l'ajout d'une salle (roomService.addRoom est de type void)
        doNothing().when(roomService).addRoom(any(Room.class));

        mockMvc.perform(post("/api/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Conference Room"))
                .andExpect(jsonPath("$.capacity").value(50));

        verify(roomService, times(1)).addRoom(any(Room.class));
    }

    // Test PUT - Mise à jour d'une salle existante
    @Test
    void shouldUpdateExistingRoom() throws Exception {
        Room updatedRoom = new Room("Updated Room", 60);
        // Simule la mise à jour d'une salle (roomService.updateRoom est de type void)
        doNothing().when(roomService).updateRoom(eq(1L), any(Room.class));

        mockMvc.perform(put("/api/rooms/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Room"))
                .andExpect(jsonPath("$.capacity").value(60));

        verify(roomService, times(1)).updateRoom(eq(1L), any(Room.class));
    }

    // Test DELETE - Suppression d'une salle par ID
    @Test
    void shouldDeleteRoomById() throws Exception {
        doNothing().when(roomService).deleteRoom(1L);

        mockMvc.perform(delete("/api/rooms/1"))
                .andExpect(status().isNoContent());

        verify(roomService, times(1)).deleteRoom(1L);
    }
}
