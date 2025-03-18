package kg.alatoo.taskplatform.service.impl;

import kg.alatoo.taskplatform.dto.task.TaskRequest;
import kg.alatoo.taskplatform.dto.task.TaskResponse;
import kg.alatoo.taskplatform.entities.Task;
import kg.alatoo.taskplatform.entities.TaskLevel;
import kg.alatoo.taskplatform.entities.User;
import kg.alatoo.taskplatform.exception.CustomException;
import kg.alatoo.taskplatform.mapper.TaskMapper;
import kg.alatoo.taskplatform.repositories.TaskLevelRepository;
import kg.alatoo.taskplatform.repositories.TaskRepository;
import kg.alatoo.taskplatform.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskLevelRepository taskLevelRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll() {
        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("Test Description");

        when(taskRepository.findAll()).thenReturn(List.of(task));
        when(taskMapper.toDtoS(any())).thenReturn(List.of(new TaskResponse()));

        var result = taskService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAllAvailableTasks() {
        Task task = new Task();
        task.setAvailable(true);

        when(taskRepository.findAll()).thenReturn(List.of(task));
        when(taskMapper.toDtoS(any())).thenReturn(List.of(new TaskResponse()));

        var result = taskService.getAllAvailableTasks();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAllAvailableTasks_noTasks() {
        when(taskRepository.findAll()).thenReturn(List.of());

        assertThrows(CustomException.class, () -> taskService.getAllAvailableTasks());
    }

    @Test
    void getAllArchivedTasks() {
        Task task = new Task();
        task.setAvailable(false);

        when(taskRepository.findAll()).thenReturn(List.of(task));
        when(taskMapper.toDtoS(any())).thenReturn(List.of(new TaskResponse()));

        var result = taskService.getAllArchivedTasks();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAllArchivedTasks_noTasks() {
        when(taskRepository.findAll()).thenReturn(List.of());

        assertThrows(CustomException.class, () -> taskService.getAllArchivedTasks());
    }

    @Test
    void getTasksByLevel() {
        String level = "Medium";
        Task task = new Task();
        TaskLevel taskLevel = new TaskLevel();
        taskLevel.setLevel(level);
        task.setTaskLevel(taskLevel);

        when(taskLevelRepository.findByLevel(level)).thenReturn(Optional.of(taskLevel));
        when(taskRepository.findAll()).thenReturn(List.of(task));
        when(taskMapper.toDtoS(any())).thenReturn(List.of(new TaskResponse()));

        var result = taskService.getTasksByLevel(level);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getTasksByLevel_invalidLevel() {
        String level = "InvalidLevel";
        when(taskLevelRepository.findByLevel(level)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> taskService.getTasksByLevel(level));
    }

    @Test
    void findByName() {
        String taskName = "Test Task";
        Task task = new Task();
        task.setName(taskName);

        when(taskRepository.findByName(taskName)).thenReturn(Optional.of(task));
        when(taskMapper.toDto(any())).thenReturn(new TaskResponse());

        var result = taskService.findByName(taskName);

        assertNotNull(result);
    }

    @Test
    void findByName_taskNotFound() {
        String taskName = "Nonexistent Task";
        when(taskRepository.findByName(taskName)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> taskService.findByName(taskName));
    }

    @Test
    void updateByName() {
        String taskName = "Test Task";
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("Updated Task");

        Task task = new Task();
        task.setName(taskName);
        task.setDescription("Test Description");

        when(taskRepository.findByName(taskName)).thenReturn(Optional.of(task));
        when(taskRepository.findByName(taskRequest.getName())).thenReturn(Optional.empty());

        taskService.updateByName(taskName, taskRequest);

        assertEquals("Updated Task", task.getName());
    }

    @Test
    void updateByName_taskAlreadyExists() {
        String taskName = "Test Task";
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("Updated Task");

        Task task = new Task();
        task.setName(taskName);
        task.setDescription("Test Description");

        when(taskRepository.findByName(taskName)).thenReturn(Optional.of(task));
        when(taskRepository.findByName(taskRequest.getName())).thenReturn(Optional.of(new Task()));

        assertThrows(CustomException.class, () -> taskService.updateByName(taskName, taskRequest));
    }

    @Test
    void cancelByName() {
        String taskName = "Test Task";
        Task task = new Task();
        task.setName(taskName);
        task.setAvailable(true);

        when(taskRepository.findByName(taskName)).thenReturn(Optional.of(task));

        taskService.cancelByName(taskName);

        assertFalse(task.isAvailable());
    }

    @Test
    void cancelByName_taskNotFound() {
        String taskName = "Nonexistent Task";
        when(taskRepository.findByName(taskName)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> taskService.cancelByName(taskName));
    }


    @Test
    void create_taskAlreadyExists() {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("Existing Task");

        when(taskRepository.findByName(taskRequest.getName())).thenReturn(Optional.of(new Task()));

        assertThrows(CustomException.class, () -> taskService.create(taskRequest, "user@example.com"));
    }

    @Test
    void assignTaskToUser() {
        String taskName = "Test Task";
        String userEmail = "user@example.com";

        Task task = new Task();
        task.setName(taskName);

        User user = new User();
        user.setEmail(userEmail);

        when(taskRepository.findByName(taskName)).thenReturn(Optional.of(task));
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));

        taskService.assignTaskToUser(taskName, userEmail);

        assertEquals(userEmail, task.getSolver());
    }

    @Test
    void assignTaskToUser_userNotFound() {
        String taskName = "Test Task";
        String userEmail = "nonexistent@example.com";

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> taskService.assignTaskToUser(taskName, userEmail));
    }
}