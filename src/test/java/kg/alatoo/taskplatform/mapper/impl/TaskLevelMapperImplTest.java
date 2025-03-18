package kg.alatoo.taskplatform.mapper.impl;

import kg.alatoo.taskplatform.dto.task_level.TaskLevelResponse;
import kg.alatoo.taskplatform.entities.TaskLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskLevelMapperImplTest {

    private TaskLevelMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new TaskLevelMapperImpl();
    }

    @Test
    void toDto_ShouldMapEntityToDto() {
        TaskLevel taskLevel = new TaskLevel();
        taskLevel.setId(1L);
        taskLevel.setLevel("medium");
        taskLevel.setPoint(50);

        TaskLevelResponse response = mapper.toDto(taskLevel);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("medium", response.getLevel());
        assertEquals(50, response.getPoint());
    }

    @Test
    void toDtoS_ShouldMapEntityListToDtoList() {
        TaskLevel taskLevel1 = new TaskLevel();
        taskLevel1.setId(1L);
        taskLevel1.setLevel("easy");
        taskLevel1.setPoint(10);

        TaskLevel taskLevel2 = new TaskLevel();
        taskLevel2.setId(2L);
        taskLevel2.setLevel("hard");
        taskLevel2.setPoint(100);

        List<TaskLevel> taskLevels = new ArrayList<>();
        taskLevels.add(taskLevel1);
        taskLevels.add(taskLevel2);

        List<TaskLevelResponse> responses = mapper.toDtoS(taskLevels);

        assertNotNull(responses);
        assertEquals(2, responses.size());

        assertEquals(1L, responses.get(0).getId());
        assertEquals("easy", responses.get(0).getLevel());
        assertEquals(10, responses.get(0).getPoint());

        assertEquals(2L, responses.get(1).getId());
        assertEquals("hard", responses.get(1).getLevel());
        assertEquals(100, responses.get(1).getPoint());
    }
}