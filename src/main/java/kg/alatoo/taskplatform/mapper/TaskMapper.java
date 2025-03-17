package kg.alatoo.taskplatform.mapper;

import kg.alatoo.taskplatform.dto.task.TaskResponse;
import kg.alatoo.taskplatform.entities.Task;

import java.util.List;

public interface TaskMapper {
    TaskResponse toDto(Task task);
    List<TaskResponse> toDtoS(List<Task> all);
}