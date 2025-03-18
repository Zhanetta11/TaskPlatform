package kg.alatoo.taskplatform.controllers;

import kg.alatoo.taskplatform.dto.task_level.TaskLevelRequest;
import kg.alatoo.taskplatform.dto.task_level.TaskLevelResponse;
import kg.alatoo.taskplatform.service.TaskLevelService;
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
class TaskLevelControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskLevelService taskLevelService;

    @InjectMocks
    private TaskLevelController taskLevelController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskLevelController).build();
    }

    @Test
    void getAll() throws Exception {
        when(taskLevelService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/taskLevel/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(taskLevelService, times(1)).getAll();
    }

    @Test
    void findByLevel() throws Exception {
        TaskLevelResponse response = new TaskLevelResponse();
        response.setLevel("easy");
        response.setPoint(10);
        when(taskLevelService.findByLevel("easy")).thenReturn(response);

        mockMvc.perform(get("/taskLevel/findByLevel/easy"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.level").value("easy"))
                .andExpect(jsonPath("$.point").value(10));

        verify(taskLevelService, times(1)).findByLevel("easy");
    }

    @Test
    void create() throws Exception {
        TaskLevelRequest request = new TaskLevelRequest();
        request.setLevel("hard");
        request.setPoint(30);

        mockMvc.perform(post("/taskLevel/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"level\":\"hard\",\"point\":30}"))
                .andExpect(status().isOk());

        verify(taskLevelService, times(1)).create(any(TaskLevelRequest.class));
    }

    @Test
    void updateByLevel() throws Exception {
        TaskLevelRequest request = new TaskLevelRequest();
        request.setLevel("medium");
        request.setPoint(20);

        mockMvc.perform(put("/taskLevel/updateByLevel/medium")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"level\":\"medium\",\"point\":20}"))
                .andExpect(status().isOk());

        verify(taskLevelService, times(1)).updateByLevel(eq("medium"), any(TaskLevelRequest.class));
    }

    @Test
    void deleteByLevel() throws Exception {
        mockMvc.perform(delete("/taskLevel/deleteByLevel/easy"))
                .andExpect(status().isOk());

        verify(taskLevelService, times(1)).deleteByLevel("easy");
    }
}