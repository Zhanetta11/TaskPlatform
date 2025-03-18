package kg.alatoo.taskplatform.controllers;

import kg.alatoo.taskplatform.dto.user.UserRequest;
import kg.alatoo.taskplatform.dto.user.UserResponse;
import kg.alatoo.taskplatform.service.UserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getAll() throws Exception {
        when(userDetailsService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/user/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findByEmail() throws Exception {
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail("test@example.com");

        when(userDetailsService.findByEmail("test@example.com")).thenReturn(userResponse);

        mockMvc.perform(get("/user/findByEmail/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void updateByEmail() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("updated@example.com");
        userRequest.setName("Updated Name");
        userRequest.setPassword("password123");

        doNothing().when(userDetailsService).updateByEmail(eq("updated@example.com"), any(UserRequest.class));

        mockMvc.perform(put("/user/updateByEmail/updated@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Name\", \"email\":\"updated@example.com\", \"password\":\"password123\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteByEmail() throws Exception {
        doNothing().when(userDetailsService).deleteByEmail("test@example.com");

        mockMvc.perform(delete("/user/deleteByEmail/test@example.com"))
                .andExpect(status().isOk());
    }

    @Test
    void register() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("new@example.com");
        userRequest.setName("New User");
        userRequest.setPassword("password123");

        doNothing().when(userDetailsService).register(any(UserRequest.class));

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New User\", \"email\":\"new@example.com\", \"password\":\"password123\"}"))
                .andExpect(status().isOk());
    }
}