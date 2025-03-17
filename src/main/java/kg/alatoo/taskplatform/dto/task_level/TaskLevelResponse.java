package kg.alatoo.taskplatform.dto.task_level;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskLevelResponse {
    private Long id;
    private String level;
    private int point;
}