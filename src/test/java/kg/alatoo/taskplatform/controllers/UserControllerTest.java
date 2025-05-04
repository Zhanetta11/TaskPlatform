package kg.alatoo.taskplatform.controllers;

import kg.alatoo.taskplatform.dto.user.UserRequest;
import kg.alatoo.taskplatform.dto.user.UserResponse;
import kg.alatoo.taskplatform.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Mock
    private UserService service;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll() {
        UserResponse user1 = new UserResponse();
        user1.setId(1L);
        user1.setName("Akylai");
        user1.setEmail("akylai@example.com");

        UserResponse user2 = new UserResponse();
        user2.setId(2L);
        user2.setName("Akylai");
        user2.setEmail("akylai2@example.com");

        when(service.getAll()).thenReturn(Arrays.asList(user1, user2));

        assertEquals(2, userController.getAll().size());
        verify(service, times(1)).getAll();
    }

    @Test
    void findByEmail() {
        UserResponse user = new UserResponse();
        user.setId(1L);
        user.setName("Akylai");
        user.setEmail("akylai@example.com");

        when(service.findByEmail("akylai@example.com")).thenReturn(user);

        UserResponse result = userController.findByEmail("akylai@example.com");

        assertNotNull(result);
        assertEquals("akylai@example.com", result.getEmail());
        verify(service, times(1)).findByEmail("akylai@example.com");
    }

    @Test
    void updateByEmail() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Akylai Updated");
        userRequest.setEmail("akylai.updated@example.com");
        userRequest.setPassword("newpassword");

        doNothing().when(service).updateByEmail(eq("akylai@example.com"), eq(userRequest));

        userController.updateByEmail("akylai@example.com", userRequest);

        verify(service, times(1)).updateByEmail(eq("akylai@example.com"), eq(userRequest));
    }

    @Test
    void deleteByEmail() {
        doNothing().when(service).deleteByEmail("akylai@example.com");

        userController.deleteByEmail("akylai@example.com");

        verify(service, times(1)).deleteByEmail("akylai@example.com");
    }

    @Test
    void register() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Akylai");
        userRequest.setEmail("akylai@example.com");
        userRequest.setPassword("password");

        doNothing().when(service).register(userRequest);

        userController.register(userRequest);

        verify(service, times(1)).register(userRequest);
    }
}