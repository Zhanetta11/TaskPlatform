package kg.alatoo.taskplatform.service.impl;

import kg.alatoo.taskplatform.dto.task_level.TaskLevelRequest;
import kg.alatoo.taskplatform.dto.task_level.TaskLevelResponse;
import kg.alatoo.taskplatform.entities.TaskLevel;
import kg.alatoo.taskplatform.mapper.TaskLevelMapper;
import kg.alatoo.taskplatform.repositories.TaskLevelRepository;
import kg.alatoo.taskplatform.service.TaskLevelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskLevelServiceImpl implements TaskLevelService {

    private final TaskLevelRepository taskLevelRepository;
    private final TaskLevelMapper taskLevelMapper;

    @Override
    public List<TaskLevelResponse> getAll() {
        return taskLevelMapper.toDtoS(taskLevelRepository.findAll());
    }

    @Override
    public TaskLevelResponse findByLevel(String level) {
        Optional<TaskLevel> taskLevel = taskLevelRepository.findByLevel(level);
        return taskLevelMapper.toDto(taskLevel.get());
    }

    @Override
    public void updateByLevel(String level, TaskLevelRequest taskLevelRequest) {
        Optional<TaskLevel> taskLevel = taskLevelRepository.findByLevel(level);
        taskLevel.get().setLevel(taskLevelRequest.getLevel());
        taskLevel.get().setPoint(taskLevelRequest.getPoint());
        taskLevelRepository.save(taskLevel.get());
    }

    @Override
    public void deleteByLevel(String level) {
        Optional<TaskLevel> taskLevel = taskLevelRepository.findByLevel(level);
        taskLevelRepository.deleteByLevel(level);
    }

    @Override
    public void create(TaskLevelRequest taskLevelRequest) {
        TaskLevel taskLevel = new TaskLevel();
        taskLevel.setLevel(taskLevelRequest.getLevel());
        taskLevel.setPoint(taskLevelRequest.getPoint());
        taskLevelRepository.save(taskLevel);
    }
}