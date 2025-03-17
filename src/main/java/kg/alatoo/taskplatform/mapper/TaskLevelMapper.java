package kg.alatoo.taskplatform.mapper;

import kg.alatoo.taskplatform.dto.task_level.TaskLevelResponse;
import kg.alatoo.taskplatform.entities.TaskLevel;

import java.util.List;

public interface TaskLevelMapper {
    TaskLevelResponse toDto(TaskLevel taskLevel);
    List<TaskLevelResponse> toDtoS(List<TaskLevel> all);
}