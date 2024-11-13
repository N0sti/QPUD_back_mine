package com.takima.backskeleton;


import com.takima.backskeleton.DAO.UserDao;
import com.takima.backskeleton.DTO.UserDto;
import com.takima.backskeleton.models.User;
import com.takima.backskeleton.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("john_doe", false);
        System.out.println("Setup completed for UserServiceTest");
    }

    @Test
    public void testAddUser() {
        // Utilisation du Builder pour créer UserDto
        UserDto userDto = UserDto.builder()
                .username("john_doe")
                .isAdmin(false)
                .build();
        System.out.println("Adding user: " + userDto.getUsername());

        userService.addUser(userDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao, times(1)).save(userCaptor.capture());
        System.out.println("User added with username: " + userCaptor.getValue().getUsername());
        assertEquals("john_doe", userCaptor.getValue().getUsername());
        assertFalse(userCaptor.getValue().isAdmin());
    }

    @Test
    public void testFindAll() {
        when(userDao.findAll()).thenReturn(Arrays.asList(user));
        System.out.println("Finding all users");

        List<UserDto> users = userService.findAll();
        assertEquals(1, users.size());
        System.out.println("Number of users found: " + users.size());
        assertEquals("john_doe", users.get(0).getUsername());
    }

    @Test
    public void testGetById() {
        when(userDao.findById(1L)).thenReturn(Optional.of(user));
        System.out.println("Getting user by ID: 1");

        UserDto foundUser = userService.getById(1L);
        System.out.println("User found: " + foundUser.getUsername());
        assertEquals("john_doe", foundUser.getUsername());
    }

    @Test
    public void testUpdateUser() {
        User updatedUser = new User("john_doe_updated", false);
        when(userDao.findById(1L)).thenReturn(Optional.of(user));
        System.out.println("Updating user with ID: 1");

        // Utilisation du Builder pour créer le UserDto mis à jour
        UserDto updatedUserDto = UserDto.builder()
                .username("john_doe_updated")
                .isAdmin(false)
                .build();

        userService.updateUser(1L, updatedUserDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao, times(1)).save(userCaptor.capture());
        System.out.println("User updated with new username: " + userCaptor.getValue().getUsername());
        assertEquals("john_doe_updated", userCaptor.getValue().getUsername());
    }

    @Test
    public void testDeleteUser() {
        System.out.println("Deleting user with ID: 1");
        userService.deleteUser(1L);
        verify(userDao, times(1)).deleteById(1L);
        System.out.println("User deleted with ID: 1");
    }
}
