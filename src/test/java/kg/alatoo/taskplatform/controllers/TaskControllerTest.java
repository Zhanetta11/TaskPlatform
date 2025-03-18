package kg.alatoo.taskplatform.controllers;

import kg.alatoo.taskplatform.dto.task.TaskRequest;
import kg.alatoo.taskplatform.dto.task.TaskResponse;
import kg.alatoo.taskplatform.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void getAll() throws Exception {
        when(taskService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/task/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(taskService, times(1)).getAll();
    }

    @Test
    void getAllAvailableTasks() throws Exception {
        when(taskService.getAllAvailableTasks()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/task/getAllAvailableTasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(taskService, times(1)).getAllAvailableTasks();
    }

    @Test
    void findByName() throws Exception {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setName("Sample Task");
        when(taskService.findByName("Sample Task")).thenReturn(taskResponse);

        mockMvc.perform(get("/task/findByName/Sample Task"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Sample Task"));

        verify(taskService, times(1)).findByName("Sample Task");
    }

    @Test
    void create() throws Exception {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("New Task");
        taskRequest.setDescription("Task description");
        taskRequest.setLevel("easy");

        mockMvc.perform(post("/task/create/test@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Task\",\"description\":\"Task description\",\"level\":\"easy\"}"))
                .andExpect(status().isOk());

        verify(taskService, times(1)).create(any(TaskRequest.class), eq("test@example.com"));
    }
}