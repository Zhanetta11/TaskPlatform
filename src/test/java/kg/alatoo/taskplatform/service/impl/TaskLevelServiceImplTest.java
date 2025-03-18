package kg.alatoo.taskplatform.service.impl;

import kg.alatoo.taskplatform.dto.task_level.TaskLevelRequest;
import kg.alatoo.taskplatform.dto.task_level.TaskLevelResponse;
import kg.alatoo.taskplatform.entities.TaskLevel;
import kg.alatoo.taskplatform.exception.CustomException;
import kg.alatoo.taskplatform.mapper.TaskLevelMapper;
import kg.alatoo.taskplatform.repositories.TaskLevelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskLevelServiceImplTest {

    @Mock
    private TaskLevelRepository taskLevelRepository;

    @Mock
    private TaskLevelMapper taskLevelMapper;

    @InjectMocks
    private TaskLevelServiceImpl taskLevelService;

    private TaskLevelRequest taskLevelRequest;
    private TaskLevel taskLevel;
    private TaskLevelResponse taskLevelResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        taskLevelRequest = new TaskLevelRequest();
        taskLevelRequest.setLevel("Hard");
        taskLevelRequest.setPoint(30);

        taskLevel = new TaskLevel();
        taskLevel.setLevel("Hard");
        taskLevel.setPoint(30);
        taskLevel.setId(1L);

        taskLevelResponse = new TaskLevelResponse();
        taskLevelResponse.setLevel("Hard");
        taskLevelResponse.setPoint(30);
        taskLevelResponse.setId(1L);
    }

    @Test
    void getAll() {
        when(taskLevelRepository.findAll()).thenReturn(List.of(taskLevel));
        when(taskLevelMapper.toDtoS(anyList())).thenReturn(List.of(taskLevelResponse));

        var result = taskLevelService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Hard", result.get(0).getLevel());
        verify(taskLevelRepository, times(1)).findAll();
    }

    @Test
    void findByLevel() {
        when(taskLevelRepository.findByLevel("Hard")).thenReturn(Optional.of(taskLevel));
        when(taskLevelMapper.toDto(any(TaskLevel.class))).thenReturn(taskLevelResponse);

        var result = taskLevelService.findByLevel("Hard");

        assertNotNull(result);
        assertEquals("Hard", result.getLevel());
        verify(taskLevelRepository, times(1)).findByLevel("Hard");
    }

    @Test
    void findByLevel_TaskLevelNotFound() {
        when(taskLevelRepository.findByLevel("NonExistent")).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> taskLevelService.findByLevel("NonExistent"));
        assertEquals("NonExistent level not found!", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void updateByLevel() {
        when(taskLevelRepository.findByLevel("Hard")).thenReturn(Optional.of(taskLevel));

        taskLevelService.updateByLevel("Hard", taskLevelRequest);

        verify(taskLevelRepository, times(1)).save(taskLevel);
    }

    @Test
    void updateByLevel_TaskLevelNotFound() {
        when(taskLevelRepository.findByLevel("NonExistent")).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> taskLevelService.updateByLevel("NonExistent", taskLevelRequest));
        assertEquals("NonExistent level not found!", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void deleteByLevel() {
        when(taskLevelRepository.findByLevel("Hard")).thenReturn(Optional.of(taskLevel));

        taskLevelService.deleteByLevel("Hard");

        verify(taskLevelRepository, times(1)).deleteByLevel("Hard");
    }

    @Test
    void deleteByLevel_TaskLevelNotFound() {
        when(taskLevelRepository.findByLevel("NonExistent")).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> taskLevelService.deleteByLevel("NonExistent"));
        assertEquals("NonExistent level not found!", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void create() {
        when(taskLevelRepository.findByLevel("NewLevel")).thenReturn(Optional.empty());

        taskLevelService.create(taskLevelRequest);

        verify(taskLevelRepository, times(1)).save(any(TaskLevel.class));
    }

    @Test
    void create_LevelAlreadyExists() {
        when(taskLevelRepository.findByLevel("Hard")).thenReturn(Optional.of(taskLevel));

        CustomException exception = assertThrows(CustomException.class, () -> taskLevelService.create(taskLevelRequest));
        assertEquals("Hard level have already exist!", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
    }
}