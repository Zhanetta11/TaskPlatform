package kg.alatoo.taskplatform.mapper.impl;

import kg.alatoo.taskplatform.dto.task.TaskResponse;
import kg.alatoo.taskplatform.entities.Task;
import kg.alatoo.taskplatform.entities.TaskLevel;
import kg.alatoo.taskplatform.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperImplTest {

    private TaskMapperImpl taskMapper;

    @BeforeEach
    void setUp() {
        taskMapper = new TaskMapperImpl();
    }

    @Test
    void toDto() {
        User user = new User();
        user.setName("John Doe");

        TaskLevel taskLevel = new TaskLevel();
        taskLevel.setLevel("easy");

        Task task = new Task();
        task.setId(1L);
        task.setName("Test Task");
        task.setDescription("Sample description");
        task.setCreatedDay(LocalDate.now());
        task.setUser(user);
        task.setSolver("Solver Name");
        task.setTaskLevel(taskLevel);

        TaskResponse taskResponse = taskMapper.toDto(task);

        assertNotNull(taskResponse);
        assertEquals(task.getId(), taskResponse.getId());
        assertEquals(task.getName(), taskResponse.getName());
        assertEquals(task.getDescription(), taskResponse.getDescription());
        assertEquals(task.getCreatedDay(), taskResponse.getCreatedDay());
        assertEquals(user.getName(), taskResponse.getAuthor());
        assertEquals(task.getSolver(), taskResponse.getSolver());
        assertEquals(taskLevel.getLevel(), taskResponse.getLevel());
    }

    @Test
    void toDtoS() {
        User user = new User();
        user.setName("John Doe");

        TaskLevel taskLevel = new TaskLevel();
        taskLevel.setLevel("medium");

        Task task1 = new Task();
        task1.setId(1L);
        task1.setName("Task One");
        task1.setDescription("First task description");
        task1.setCreatedDay(LocalDate.now());
        task1.setUser(user);
        task1.setSolver("Solver One");
        task1.setTaskLevel(taskLevel);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setName("Task Two");
        task2.setDescription("Second task description");
        task2.setCreatedDay(LocalDate.now());
        task2.setUser(user);
        task2.setSolver("Solver Two");
        task2.setTaskLevel(taskLevel);

        List<TaskResponse> responses = taskMapper.toDtoS(List.of(task1, task2));

        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("Task One", responses.get(0).getName());
        assertEquals("Task Two", responses.get(1).getName());
    }
}