package kg.alatoo.taskplatform.service.impl;

import kg.alatoo.taskplatform.dto.task.TaskRequest;
import kg.alatoo.taskplatform.dto.task.TaskResponse;
import kg.alatoo.taskplatform.entities.Task;
import kg.alatoo.taskplatform.entities.TaskLevel;
import kg.alatoo.taskplatform.entities.User;
import kg.alatoo.taskplatform.mapper.TaskMapper;
import kg.alatoo.taskplatform.repositories.TaskLevelRepository;
import kg.alatoo.taskplatform.repositories.TaskRepository;
import kg.alatoo.taskplatform.repositories.UserRepository;
import kg.alatoo.taskplatform.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final TaskLevelRepository taskLevelRepository;
    @Override
    public List<TaskResponse> getAll() {
        return taskMapper.toDtoS(taskRepository.findAll());
    }

    @Override
    public List<TaskResponse> getAllAvailableTasks() {
        List<Task> tasks = new ArrayList<>();
        for(Task task : taskRepository.findAll()) {
            if(task.isAvailable()) {
                tasks.add(task);
            }
        }
        return taskMapper.toDtoS(tasks);
    }

    @Override
    public List<TaskResponse> getAllArchivedTasks() {
        List<Task> tasks = new ArrayList<>();
        for(Task task : taskRepository.findAll()) {
            if(!task.isAvailable()) {
                tasks.add(task);
            }
        }
        return taskMapper.toDtoS(tasks);
    }

    @Override
    public List<TaskResponse> getTasksByLevel(String level) {
        List<Task> tasks = new ArrayList<>();
        for(Task task : taskRepository.findAll()) {
            if(task.getTaskLevel().getLevel().equals(level)) {
                tasks.add(task);
            }
        }
        return taskMapper.toDtoS(tasks);
    }

    @Override
    public TaskResponse findByName(String name) {
        Optional<Task> task = taskRepository.findByName(name);
        return taskMapper.toDto(task.get());
    }

    @Override
    public void updateByName(String name, TaskRequest taskRequest) {
        Optional<Task> task = taskRepository.findByName(name);
        task.get().setName(taskRequest.getName());
        task.get().setDescription(taskRequest.getDescription());
        taskRepository.save(task.get());
    }

    @Override
    public void cancelByName(String name) {
        Optional<Task> task = taskRepository.findByName(name);
        task.get().setAvailable(false);
        taskRepository.save(task.get());
    }

    @Override
    public void assignTaskToUser(String taskName, String userEmail) {
        Optional<Task> task = taskRepository.findByName(taskName);
        Optional<User> user = userRepository.findByEmail(userEmail);

        task.get().setSolver(userEmail);
        taskRepository.save(task.get());
    }
}