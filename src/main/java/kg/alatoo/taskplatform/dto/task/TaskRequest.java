package kg.alatoo.taskplatform.dto.task;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {
    private String name;
    private String description;
    private String level;
}