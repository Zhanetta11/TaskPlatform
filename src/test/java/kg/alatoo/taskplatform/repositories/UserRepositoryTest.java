package kg.alatoo.taskplatform.repositories;

import kg.alatoo.taskplatform.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user1 = new User();
        user1.setName("Altynai");
        user1.setEmail("altynai@example.com");
        user1.setRole("ADMIN");
        user1.setPassword("password123");
        userRepository.save(user1);

        User user2 = new User();
        user2.setName("Aidai");
        user2.setEmail("aidai@example.com");
        user2.setRole("USER");
        user2.setPassword("pass456");
        userRepository.save(user2);
    }

    @Test
    void findByEmail_ShouldReturnUser_WhenEmailExists() {
        Optional<User> found = userRepository.findByEmail("altynai@example.com");
        assertTrue(found.isPresent());
        assertEquals("Altynai", found.get().getName());
        assertEquals("ADMIN", found.get().getRole());
    }

    @Test
    void findByEmail_ShouldReturnEmpty_WhenEmailDoesNotExist() {
        Optional<User> found = userRepository.findByEmail("nonexistent@example.com");
        assertFalse(found.isPresent());
    }

    @Test
    void deleteByEmail_ShouldRemoveUser_WhenEmailExists() {
        userRepository.deleteByEmail("aidai@example.com");
        Optional<User> found = userRepository.findByEmail("aidai@example.com");
        assertFalse(found.isPresent());
    }

    @Test
    void createUser_ShouldPersistAndRetrieve() {
        User newUser = new User();
        newUser.setName("Akylai");
        newUser.setEmail("akylai@example.com");
        newUser.setRole("USER");
        newUser.setPassword("strongpass");
        userRepository.save(newUser);

        Optional<User> found = userRepository.findByEmail("akylai@example.com");
        assertTrue(found.isPresent());
        assertEquals("Akylai", found.get().getName());
        assertEquals("USER", found.get().getRole());
    }
}