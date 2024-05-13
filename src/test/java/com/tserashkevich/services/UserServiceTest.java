package com.tserashkevich.services;
import com.tserashkevich.models.User;
import com.tserashkevich.models.enums.Role;
import com.tserashkevich.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void findAll_ShouldReturnListOfUsers() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.findAll();

        // Assert
        assertEquals(users, result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findOne_ExistingId_ShouldReturnUser() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        User result = userService.findOne(userId);

        // Assert
        assertEquals(user, result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void findOne_NonExistingId_ShouldReturnNull() {
        // Arrange
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        User result = userService.findOne(userId);

        // Assert
        assertNull(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void save_ShouldSaveUserWithEncodedPasswordAndSetUserRole() {
        // Arrange
        User user = new User();
        user.setPassword("password");

        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        // Act
        userService.save(user);

        // Assert
        assertEquals(Role.USER, user.getRole());
        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void changeRole_ExistingUserWithUserRole_ShouldChangeRoleToAdmin() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setRole(Role.USER);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.changeRole(userId);

        // Assert
        assertEquals(Role.ADMIN, user.getRole());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void changeRole_ExistingUserWithAdminRole_ShouldChangeRoleToUser() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setRole(Role.ADMIN);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.changeRole(userId);

        // Assert
        assertEquals(Role.USER, user.getRole());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void changeRole_NonExistingUser_ShouldNotChangeRole() {
        // Arrange
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        userService.changeRole(userId);

        // Assert
        verify(userRepository, never()).save(any());
    }

    @Test
    void delete_ShouldDeleteUser() {
        // Arrange
        Long userId = 1L;

        // Act
        userService.delete(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void getUserByLogin_ExistingUser_ShouldReturnUser() {
        // Arrange
        String login = "johnDoe";
        User user = new User();

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.findUserByLogin(login);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findByLogin(login);
    }

    @Test
    void getUserByLogin_NonExistingUser_ShouldReturnEmptyOptional() {
        // Arrange
        String login = "johnDoe";

        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.findUserByLogin(login);

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByLogin(login);
    }

    @Test
    void getUserByPhoneNumber_ExistingUser_ShouldReturnUser() {
        // Arrange
        String phoneNumber = "123456789";
        User user = new User();

        when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.getUserByPhoneNumber(phoneNumber);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findByPhoneNumber(phoneNumber);
    }

    @Test
    void getUserByPhoneNumber_NonExistingUser_ShouldReturnEmptyOptional() {
        // Arrange
        String phoneNumber = "123456789";

        when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.getUserByPhoneNumber(phoneNumber);

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByPhoneNumber(phoneNumber);
    }
}
