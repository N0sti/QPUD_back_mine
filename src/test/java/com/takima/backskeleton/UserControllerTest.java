package com.takima.backskeleton;

import com.takima.backskeleton.DTO.UserDto;
import com.takima.backskeleton.controllers.UserController;
import com.takima.backskeleton.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        System.out.println("Setup completed for UserControllerTest");
    }

    @Test
    public void testAddUser() throws Exception {
        UserDto userDto = UserDto.builder()
                .username("john_doe")
                .isAdmin(false)
                .build();
        System.out.println("Testing add user with username: " + userDto.getUsername());

        doNothing().when(userService).addUser(any(UserDto.class));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"john_doe\", \"isAdmin\": false}"))
                .andExpect(status().isOk());

        System.out.println("User added successfully");
        verify(userService, times(1)).addUser(any(UserDto.class));
    }

    @Test
    public void testGetUserById() throws Exception {
        UserDto userDto = UserDto.builder()
                .username("john_doe")
                .isAdmin(false)
                .build();
        System.out.println("Testing get user by ID: 1");

        when(userService.getById(1L)).thenReturn(userDto);

        mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john_doe"));

        System.out.println("User retrieved: " + userDto.getUsername());
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserDto userDto = UserDto.builder()
                .username("john_doe_updated")
                .isAdmin(false)
                .build();
        System.out.println("Testing update user with ID: 1");

        doNothing().when(userService).updateUser(eq(1L), any(UserDto.class));

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"john_doe_updated\", \"isAdmin\": false}"))
                .andExpect(status().isOk());

        System.out.println("User updated successfully");
        verify(userService, times(1)).updateUser(eq(1L), any(UserDto.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        System.out.println("Testing delete user with ID: 1");
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());

        System.out.println("User deleted successfully");
        verify(userService, times(1)).deleteUser(1L);
    }
}
