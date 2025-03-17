package kg.alatoo.taskplatform.service;

import kg.alatoo.taskplatform.dto.task.TaskRequest;
import kg.alatoo.taskplatform.dto.task.TaskResponse;

import java.util.List;

public interface TaskService {
    List<TaskResponse> getAll();
    List<TaskResponse> getAllAvailableTasks();
    List<TaskResponse> getAllArchivedTasks();
    List<TaskResponse> getTasksByLevel(String level);
    TaskResponse findByName(String name);
    void updateByName(String name, TaskRequest taskRequest);
    void cancelByName(String name);
    void create(TaskRequest taskRequest, String userEmail);
    void assignTaskToUser(String taskName, String userEmail);
}