package kg.alatoo.taskplatform.repositories;

import kg.alatoo.taskplatform.entities.Task;
import kg.alatoo.taskplatform.entities.TaskLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskLevelRepository taskLevelRepository;

    @BeforeEach
    void setUp() {
        TaskLevel easy = new TaskLevel();
        easy.setLevel("easy");
        easy.setPoint(10);
        taskLevelRepository.save(easy);

        TaskLevel medium = new TaskLevel();
        medium.setLevel("medium");
        medium.setPoint(20);
        taskLevelRepository.save(medium);

        TaskLevel hard = new TaskLevel();
        hard.setLevel("hard");
        hard.setPoint(30);
        taskLevelRepository.save(hard);

        Task task1 = new Task();
        task1.setName("Task One");
        task1.setDescription("This is task one");
        task1.setAvailable(true);
        task1.setCreatedDay(LocalDate.now());
        task1.setTaskLevel(easy);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setName("Task Two");
        task2.setDescription("This is task two");
        task2.setAvailable(false);
        task2.setCreatedDay(LocalDate.now());
        task2.setTaskLevel(medium);
        taskRepository.save(task2);
    }

    @Test
    void findByName_ShouldReturnTask_WhenNameExists() {
        Optional<Task> found = taskRepository.findByName("Task One");
        assertTrue(found.isPresent());
        assertEquals("Task One", found.get().getName());
        assertEquals("This is task one", found.get().getDescription());
        assertEquals("easy", found.get().getTaskLevel().getLevel());
        assertTrue(found.get().isAvailable());
    }

    @Test
    void findByName_ShouldReturnEmpty_WhenNameDoesNotExist() {
        Optional<Task> found = taskRepository.findByName("NonExistentTask");
        assertFalse(found.isPresent());
    }

    @Test
    void createTask_ShouldPersistAndRetrieve() {
        Task newTask = new Task();
        newTask.setName("Task Three");
        newTask.setDescription("This is task three");
        newTask.setAvailable(true);
        newTask.setCreatedDay(LocalDate.now());
        TaskLevel hardLevel = taskLevelRepository.findByLevel("hard").orElseThrow();
        newTask.setTaskLevel(hardLevel);
        taskRepository.save(newTask);

        Optional<Task> found = taskRepository.findByName("Task Three");
        assertTrue(found.isPresent());
        assertEquals("Task Three", found.get().getName());
        assertEquals("hard", found.get().getTaskLevel().getLevel());
    }
}