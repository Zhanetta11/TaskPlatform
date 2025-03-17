package kg.alatoo.taskplatform.service.impl;

import kg.alatoo.taskplatform.dto.task_level.TaskLevelRequest;
import kg.alatoo.taskplatform.dto.task_level.TaskLevelResponse;
import kg.alatoo.taskplatform.entities.TaskLevel;
import kg.alatoo.taskplatform.exception.CustomException;
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
        checker(taskLevel, level);
        return taskLevelMapper.toDto(taskLevel.get());
    }

    @Override
    public void updateByLevel(String level, TaskLevelRequest taskLevelRequest) {
        Optional<TaskLevel> taskLevel = taskLevelRepository.findByLevel(level);
        checker(taskLevel, level);
        taskLevel.get().setLevel(taskLevelRequest.getLevel());
        taskLevel.get().setPoint(taskLevelRequest.getPoint());
        taskLevelRepository.save(taskLevel.get());
    }

    @Override
    public void deleteByLevel(String level) {
        Optional<TaskLevel> taskLevel = taskLevelRepository.findByLevel(level);
        checker(taskLevel, level);
        taskLevelRepository.deleteByLevel(level);
    }

    @Override
    public void create(TaskLevelRequest taskLevelRequest) {
        if(taskLevelRepository.findByLevel(taskLevelRequest.getLevel()).isPresent()) {
            throw new CustomException(taskLevelRequest.getLevel() + " level have already exist!", HttpStatus.CONFLICT);
        }
        TaskLevel taskLevel = new TaskLevel();
        taskLevel.setLevel(taskLevelRequest.getLevel());
        taskLevel.setPoint(taskLevelRequest.getPoint());
        taskLevelRepository.save(taskLevel);
    }

    private void checker(Optional<TaskLevel> taskLevel, String level) {
        if(taskLevel.isEmpty()) {
            throw new CustomException(level + " level not found!", HttpStatus.NOT_FOUND);
        }
    }
}