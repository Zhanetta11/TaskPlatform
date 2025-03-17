package kg.alatoo.taskplatform.dto.task;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class TaskRequest {

    @NotEmpty(message = "Task name is required")
    @Size(max = 100, message = "Task name cannot exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotEmpty(message = "Level is required")
    private String level;
}