package kg.alatoo.taskplatform.controllers;

import kg.alatoo.taskplatform.dto.task_level.TaskLevelRequest;
import kg.alatoo.taskplatform.dto.task_level.TaskLevelResponse;
import kg.alatoo.taskplatform.service.TaskLevelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskLevelControllerTest {

    @Mock
    private TaskLevelService taskLevelService;

    @InjectMocks
    private TaskLevelController taskLevelController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll() {
        TaskLevelResponse taskLevel1 = new TaskLevelResponse();
        taskLevel1.setId(1L);
        taskLevel1.setLevel("easy");
        taskLevel1.setPoint(10);

        TaskLevelResponse taskLevel2 = new TaskLevelResponse();
        taskLevel2.setId(2L);
        taskLevel2.setLevel("medium");
        taskLevel2.setPoint(20);

        when(taskLevelService.getAll()).thenReturn(Arrays.asList(taskLevel1, taskLevel2));

        assertEquals(2, taskLevelController.getAll().size());
        verify(taskLevelService, times(1)).getAll();
    }

    @Test
    void findByLevel() {
        TaskLevelResponse taskLevel = new TaskLevelResponse();
        taskLevel.setId(1L);
        taskLevel.setLevel("easy");
        taskLevel.setPoint(10);

        when(taskLevelService.findByLevel("easy")).thenReturn(taskLevel);

        TaskLevelResponse result = taskLevelController.findByLevel("easy");

        assertNotNull(result);
        assertEquals("easy", result.getLevel());
        assertEquals(10, result.getPoint());
        verify(taskLevelService, times(1)).findByLevel("easy");
    }

    @Test
    void updateByLevel() {
        TaskLevelRequest taskLevelRequest = new TaskLevelRequest();
        taskLevelRequest.setLevel("medium");
        taskLevelRequest.setPoint(25);

        doNothing().when(taskLevelService).updateByLevel(eq("easy"), eq(taskLevelRequest));

        taskLevelController.updateByLevel("easy", taskLevelRequest);

        verify(taskLevelService, times(1)).updateByLevel(eq("easy"), eq(taskLevelRequest));
    }

    @Test
    void deleteByLevel() {
        doNothing().when(taskLevelService).deleteByLevel("medium");

        taskLevelController.deleteByLevel("medium");

        verify(taskLevelService, times(1)).deleteByLevel("medium");
    }

    @Test
    void create() {
        TaskLevelRequest taskLevelRequest = new TaskLevelRequest();
        taskLevelRequest.setLevel("hard");
        taskLevelRequest.setPoint(30);

        doNothing().when(taskLevelService).create(taskLevelRequest);

        taskLevelController.create(taskLevelRequest);

        verify(taskLevelService, times(1)).create(taskLevelRequest);
    }
}