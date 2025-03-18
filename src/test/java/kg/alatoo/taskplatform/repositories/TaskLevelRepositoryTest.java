package kg.alatoo.taskplatform.repositories;

import kg.alatoo.taskplatform.entities.TaskLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskLevelRepositoryTest {

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
    }

    @Test
    void findByLevel_ShouldReturnTaskLevel_WhenLevelExists() {
        Optional<TaskLevel> found = taskLevelRepository.findByLevel("medium");
        assertTrue(found.isPresent());
        assertEquals("medium", found.get().getLevel());
        assertEquals(20, found.get().getPoint());
    }

    @Test
    void findByLevel_ShouldReturnEmpty_WhenLevelDoesNotExist() {
        Optional<TaskLevel> found = taskLevelRepository.findByLevel("nonexistent");
        assertFalse(found.isPresent());
    }

    @Test
    void deleteByLevel_ShouldDeleteTaskLevel_WhenLevelExists() {
        taskLevelRepository.deleteByLevel("easy");
        Optional<TaskLevel> found = taskLevelRepository.findByLevel("easy");
        assertFalse(found.isPresent());
    }

    @Test
    void deleteByLevel_ShouldNotThrowException_WhenLevelDoesNotExist() {
        assertDoesNotThrow(() -> taskLevelRepository.deleteByLevel("nonexistent"));
    }
}