package kg.alatoo.taskplatform.repositories;

import kg.alatoo.taskplatform.entities.TaskLevel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskLevelRepository extends JpaRepository<TaskLevel, Long> {
    @Transactional
    void deleteByLevel(String level);
    Optional<TaskLevel> findByLevel(String level);
}   