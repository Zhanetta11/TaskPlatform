package kg.alatoo.taskplatform.repositories;

import kg.alatoo.taskplatform.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    void deleteByEmail(String email);
    Optional<User> findByEmail(String email);
}