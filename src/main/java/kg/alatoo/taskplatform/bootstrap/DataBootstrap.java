package kg.alatoo.taskplatform.bootstrap;

import kg.alatoo.taskplatform.entities.User;
import kg.alatoo.taskplatform.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataBootstrap implements CommandLineRunner {
    private final UserRepository userRepository;
    public DataBootstrap(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            addDefaultUsers();
        }
    }

    private void addDefaultUsers() {
        User user1 = new User();
        user1.setName("Alim");
        user1.setEmail("alim@gmail.com");
        user1.setRole("User");
        user1.setPassword("password123");
        userRepository.save(user1);

        User user2 = new User();
        user2.setName("Merim");
        user2.setEmail("merim@gmail.com");
        user2.setRole("ADMIN");
        user2.setPassword("password123");
        userRepository.save(user2);
    }
}