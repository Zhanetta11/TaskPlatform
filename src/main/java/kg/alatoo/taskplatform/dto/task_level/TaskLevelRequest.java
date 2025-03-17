package kg.alatoo.taskplatform.dto.task_level;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskLevelRequest {

    @NotBlank(message = "Level must not be blank")
    private String level;

    @Min(value = 1, message = "Point must be greater than or equal to 1")
    private int point;
}
