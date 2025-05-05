package kg.alatoo.taskplatform.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import kg.alatoo.taskplatform.dto.task.TaskRequest;
import kg.alatoo.taskplatform.dto.task.TaskResponse;
import kg.alatoo.taskplatform.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/task")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class TaskController {
    private final TaskService service;

    @GetMapping("/getAll")
    public List<TaskResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/getAllAvailableTasks")
    public List<TaskResponse> getAllAvailableTasks() {
        return service.getAllAvailableTasks();
    }

    @GetMapping("/getAllArchive")
    public List<TaskResponse> getAllArchive() {
        return service.getAllArchivedTasks();
    }

    @Validated
    @GetMapping("/getByLevel/{level}")
    public List<TaskResponse> getTasksByLevel(@PathVariable @Valid @Pattern(regexp = "easy|medium|hard") String level) {
        return service.getTasksByLevel(level);
    }
    @GetMapping("/findByName/{name}")
    public TaskResponse findByName(@PathVariable @Valid String name) {
        return service.findByName(name);
    }

    @PutMapping("/updateByName/{name}")
    public void updateByName(@PathVariable String name,@RequestBody @Valid TaskRequest taskRequest) {
        service.updateByName(name, taskRequest);
    }

    @PutMapping("/cancelByName/{name}")
    public void cancelByName(@PathVariable String name) {
        service.cancelByName(name);
    }

    @PostMapping("/create/{userEmail}")
    public void create(@RequestBody @Valid  TaskRequest taskRequest, @PathVariable String userEmail) {
        service.create(taskRequest, userEmail);
    }

    @PutMapping("/assignTaskToUser/{taskName}/user/{userEmail}")
    public void assignTaskToUser(@PathVariable String taskName,@PathVariable String userEmail) {
        service.assignTaskToUser(taskName, userEmail);
    }
}