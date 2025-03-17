package kg.alatoo.taskplatform.mapper.impl;

import kg.alatoo.taskplatform.dto.task.TaskResponse;
import kg.alatoo.taskplatform.entities.Task;
import kg.alatoo.taskplatform.mapper.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskMapperImpl implements TaskMapper {
    @Override
    public TaskResponse toDto(Task task) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setName(task.getName());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setCreatedDay(task.getCreatedDay());
        taskResponse.setAuthor(task.getUser().getName());
        taskResponse.setSolver(task.getSolver());
        taskResponse.setLevel(task.getTaskLevel().getLevel());

        return taskResponse;
    }

    @Override
    public List<TaskResponse> toDtoS(List<Task> all) {
        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task task : all) {
            taskResponses.add(toDto(task));
        }
        return taskResponses;
    }
}