package kg.alatoo.taskplatform.controllers;

import kg.alatoo.taskplatform.dto.task.TaskRequest;
import kg.alatoo.taskplatform.dto.task.TaskResponse;
import kg.alatoo.taskplatform.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll() {
        List<TaskResponse> tasks = List.of(new TaskResponse(), new TaskResponse());
        when(taskService.getAll()).thenReturn(tasks);

        List<TaskResponse> result = taskController.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(taskService, times(1)).getAll();
    }

    @Test
    void getAllAvailableTasks() {
        List<TaskResponse> tasks = List.of(new TaskResponse());
        when(taskService.getAllAvailableTasks()).thenReturn(tasks);

        List<TaskResponse> result = taskController.getAllAvailableTasks();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(taskService, times(1)).getAllAvailableTasks();
    }

    @Test
    void getAllArchive() {
        List<TaskResponse> tasks = List.of(new TaskResponse());
        when(taskService.getAllArchivedTasks()).thenReturn(tasks);

        List<TaskResponse> result = taskController.getAllArchive();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(taskService, times(1)).getAllArchivedTasks();
    }

    @Test
    void getTasksByLevel() {
        List<TaskResponse> tasks = List.of(new TaskResponse());
        when(taskService.getTasksByLevel("easy")).thenReturn(tasks);

        List<TaskResponse> result = taskController.getTasksByLevel("easy");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(taskService, times(1)).getTasksByLevel("easy");
    }

    @Test
    void findByName() {
        TaskResponse task = new TaskResponse();
        when(taskService.findByName("taskName")).thenReturn(task);

        TaskResponse result = taskController.findByName("taskName");

        assertNotNull(result);
        verify(taskService, times(1)).findByName("taskName");
    }

    @Test
    void updateByName() {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("Updated Task");
        taskRequest.setDescription("Updated Description");
        taskRequest.setLevel("medium");

        doNothing().when(taskService).updateByName("taskName", taskRequest);

        taskController.updateByName("taskName", taskRequest);

        verify(taskService, times(1)).updateByName("taskName", taskRequest);
    }

    @Test
    void cancelByName() {
        doNothing().when(taskService).cancelByName("taskName");

        taskController.cancelByName("taskName");

        verify(taskService, times(1)).cancelByName("taskName");
    }

    @Test
    void create() {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("New Task");
        taskRequest.setDescription("Task Description");
        taskRequest.setLevel("easy");

        doNothing().when(taskService).create(taskRequest, "azim@example.com");

        taskController.create(taskRequest, "azim@example.com");

        verify(taskService, times(1)).create(taskRequest, "azim@example.com");
    }

    @Test
    void assignTaskToUser() {
        doNothing().when(taskService).assignTaskToUser("taskName", "azim@example.com");

        taskController.assignTaskToUser("taskName", "azim@example.com");

        verify(taskService, times(1)).assignTaskToUser("taskName", "azim@example.com");
    }
}