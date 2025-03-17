package kg.alatoo.taskplatform.service.impl;

import kg.alatoo.taskplatform.dto.task.TaskRequest;
import kg.alatoo.taskplatform.dto.task.TaskResponse;
import kg.alatoo.taskplatform.entities.Task;
import kg.alatoo.taskplatform.entities.TaskLevel;
import kg.alatoo.taskplatform.entities.User;
import kg.alatoo.taskplatform.exception.CustomException;
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
        if(tasks.isEmpty()) {
            throw new CustomException("There is no any available tasks! ", HttpStatus.NOT_FOUND);
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
        if(tasks.isEmpty()) {
            throw new CustomException("There is no any task in archive!", HttpStatus.NOT_FOUND);
        }
        return taskMapper.toDtoS(tasks);
    }

    @Override
    public List<TaskResponse> getTasksByLevel(String level) {
        if(taskLevelRepository.findByLevel(level).isEmpty()) {
            throw new CustomException("There is no like this (" + level + ") level in system", HttpStatus.BAD_REQUEST);
        }
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
        checker(task, name);
        return taskMapper.toDto(task.get());
    }

    @Override
    public void updateByName(String name, TaskRequest taskRequest) {
        Optional<Task> task = taskRepository.findByName(name);
        checker(task, name);
        if(taskRepository.findByName(taskRequest.getName()).isPresent() && !name.equals(taskRequest.getName())) {
            throw new CustomException("Task with name " + taskRequest.getName() + " already exist!", HttpStatus.CONFLICT);
        }
        task.get().setName(taskRequest.getName());
        task.get().setDescription(taskRequest.getDescription());
        taskRepository.save(task.get());
    }

    @Override
    public void cancelByName(String name) {
        Optional<Task> task = taskRepository.findByName(name);
        checker(task, name);
        task.get().setAvailable(false);
        taskRepository.save(task.get());
    }

    @Override
    public void create(TaskRequest taskRequest, String userEmail) {
        if (taskRepository.findByName(taskRequest.getName()).isPresent()) {
            throw new CustomException("Task with name " + taskRequest.getName() + " already exist!", HttpStatus.CONFLICT);
        }
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isEmpty()) {
            throw new CustomException("User with email " + userEmail + " is not found", HttpStatus.NOT_FOUND);
        }
        Optional<TaskLevel> taskLevel = taskLevelRepository.findByLevel(taskRequest.getLevel());
        if(taskLevel.isEmpty()) {
            throw new CustomException(taskRequest.getLevel() + " level does not exist in system!", HttpStatus.NOT_FOUND);
        }
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setDescription(taskRequest.getDescription());
        task.setAvailable(true);
        task.setTaskLevel(taskLevel.get());
        task.setCreatedDay(LocalDate.now());
        task.setUser(user.get());
        taskRepository.save(task);
        user.get().getTasks().add(task);
        userRepository.save(user.get());
    }

    @Override
    public void assignTaskToUser(String taskName, String userEmail) {
        Optional<Task> task = taskRepository.findByName(taskName);
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isEmpty()) {
            throw new CustomException("User with email " + userEmail + " is not found", HttpStatus.NOT_FOUND);
        }
        task.get().setSolver(userEmail);
        taskRepository.save(task.get());
    }

    private void checker(Optional<Task> task, String name) {
        if(task.isEmpty()) {
            throw new CustomException("Task is not found with name: " + name, HttpStatus.NOT_FOUND);
        }
    }
}