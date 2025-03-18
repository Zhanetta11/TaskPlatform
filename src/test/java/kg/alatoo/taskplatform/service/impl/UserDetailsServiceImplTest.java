package kg.alatoo.taskplatform.service.impl;

import kg.alatoo.taskplatform.dto.user.UserRequest;
import kg.alatoo.taskplatform.dto.user.UserResponse;
import kg.alatoo.taskplatform.entities.User;
import kg.alatoo.taskplatform.exception.CustomException;
import kg.alatoo.taskplatform.mapper.UserMapper;
import kg.alatoo.taskplatform.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User user;
    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setName("Akylai");
        user.setEmail("akylai@example.com");
        user.setRole("USER");
        user.setPassword("password123");

        userRequest = new UserRequest();
        userRequest.setName("Akylai");
        userRequest.setEmail("akylai@example.com");
        userRequest.setPassword("password123");

        userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setName("Akylai");
        userResponse.setEmail("akylai@example.com");
        userResponse.setRole("USER");
        userResponse.setPassword("password123");
    }

    @Test
    void getAll() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDtoS(List.of(user))).thenReturn(List.of(userResponse));

        var result = userDetailsService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Akylai", result.get(0).getName());
    }

    @Test
    void findByEmail() {
        when(userRepository.findByEmail("akylai@example.com")).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userResponse);

        var result = userDetailsService.findByEmail("akylai@example.com");

        assertNotNull(result);
        assertEquals("akylai@example.com", result.getEmail());
    }

    @Test
    void findByEmail_UserNotFound() {
        when(userRepository.findByEmail("akylai@example.com")).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            userDetailsService.findByEmail("akylai@example.com");
        });

        assertEquals("User is not found with the email: akylai@example.com", exception.getMessage());
    }

    @Test
    void updateByEmail() {
        when(userRepository.findByEmail("akylai@example.com")).thenReturn(Optional.of(user));

        UserRequest updatedRequest = new UserRequest();
        updatedRequest.setName("Akylai Updated");
        updatedRequest.setEmail("akylai.updated@example.com");
        updatedRequest.setPassword("newpassword123");

        userDetailsService.updateByEmail("akylai@example.com", updatedRequest);

        assertEquals("Akylai Updated", user.getName());
        assertEquals("akylai.updated@example.com", user.getEmail());
    }

    @Test
    void updateByEmail_UserNotFound() {
        when(userRepository.findByEmail("akylai@example.com")).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            userDetailsService.updateByEmail("akylai@example.com", userRequest);
        });

        assertEquals("User is not found with the email: akylai@example.com", exception.getMessage());
    }

    @Test
    void updateByEmail_ConflictEmail() {
        when(userRepository.findByEmail("akylai@example.com")).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("akylai.updated@example.com")).thenReturn(Optional.of(new User()));

        UserRequest updatedRequest = new UserRequest();
        updatedRequest.setName("Akylai Updated");
        updatedRequest.setEmail("akylai.updated@example.com");
        updatedRequest.setPassword("newpassword123");

        CustomException exception = assertThrows(CustomException.class, () -> {
            userDetailsService.updateByEmail("akylai@example.com", updatedRequest);
        });

        assertEquals("user with email: akylai.updated@example.com already exist!", exception.getMessage());
    }

    @Test
    void deleteByEmail() {
        when(userRepository.findByEmail("akylai@example.com")).thenReturn(Optional.of(user));

        userDetailsService.deleteByEmail("akylai@example.com");

        verify(userRepository, times(1)).deleteByEmail("akylai@example.com");
    }

    @Test
    void deleteByEmail_UserNotFound() {
        when(userRepository.findByEmail("akylai@example.com")).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            userDetailsService.deleteByEmail("akylai@example.com");
        });

        assertEquals("User is not found with the email: akylai@example.com", exception.getMessage());
    }

    @Test
    void register() {
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.empty());

        userDetailsService.register(userRequest);

        verify(userRepository, times(1)).save(any(User.class));
    }
}