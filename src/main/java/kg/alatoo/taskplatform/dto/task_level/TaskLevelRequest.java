package kg.alatoo.taskplatform.dto.task_level;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskLevelRequest {
    private String level;
    private int point;
}