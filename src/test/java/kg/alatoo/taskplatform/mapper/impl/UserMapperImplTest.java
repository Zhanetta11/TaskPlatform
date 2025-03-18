package kg.alatoo.taskplatform.mapper.impl;

import kg.alatoo.taskplatform.dto.user.UserResponse;
import kg.alatoo.taskplatform.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperImplTest {

    private UserMapperImpl userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapperImpl();
    }

    @Test
    void toDto() {
        User user = new User();
        user.setId(1L);
        user.setName("Azim");
        user.setEmail("azim@example.com");
        user.setRole("ADMIN");
        user.setPassword("password123");

        UserResponse response = userMapper.toDto(user);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Azim", response.getName());
        assertEquals("azim@example.com", response.getEmail());
        assertEquals("ADMIN", response.getRole());
        assertEquals("password123", response.getPassword());
    }

    @Test
    void toDtoS() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Azim");
        user1.setEmail("azim@example.com");
        user1.setRole("ADMIN");
        user1.setPassword("password123");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Sasha");
        user2.setEmail("sasha@example.com");
        user2.setRole("USER");
        user2.setPassword("password456");

        List<UserResponse> responses = userMapper.toDtoS(List.of(user1, user2));

        assertNotNull(responses);
        assertEquals(2, responses.size());

        UserResponse response1 = responses.get(0);
        assertEquals(1L, response1.getId());
        assertEquals("Azim", response1.getName());

        UserResponse response2 = responses.get(1);
        assertEquals(2L, response2.getId());
        assertEquals("Sasha", response2.getName());
    }
}