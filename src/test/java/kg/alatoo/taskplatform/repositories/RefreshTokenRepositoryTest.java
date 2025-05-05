package kg.alatoo.taskplatform.repositories;

import kg.alatoo.taskplatform.entities.RefreshToken;
import kg.alatoo.taskplatform.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RefreshTokenRepositoryTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByToken_ShouldReturnToken() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user = userRepository.save(user);

        RefreshToken token = new RefreshToken();
        token.setToken("sample-token");
        token.setUser(user);
        token.setExpiryDate(Instant.now().plusSeconds(3600));
        refreshTokenRepository.save(token);

        Optional<RefreshToken> found = refreshTokenRepository.findByToken("sample-token");

        assertThat(found).isPresent();
        assertThat(found.get().getToken()).isEqualTo("sample-token");
        assertThat(found.get().getUser().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void deleteByUser_ShouldDeleteTokensForUser() {
        User user = new User();
        user.setEmail("deleteuser@example.com");
        user.setPassword("password123");
        user = userRepository.save(user);

        RefreshToken token = new RefreshToken();
        token.setToken("token-to-delete");
        token.setUser(user);
        token.setExpiryDate(Instant.now().plusSeconds(3600));
        refreshTokenRepository.save(token);

        refreshTokenRepository.deleteByUser(user);

        Optional<RefreshToken> result = refreshTokenRepository.findByToken("token-to-delete");
        assertThat(result).isEmpty();
    }

    @Test
    void deleteInBatch_ShouldRemoveAllTokens() {

        User user1 = new User();
        user1.setEmail("batchuser1@example.com");
        user1.setPassword("password123");
        user1 = userRepository.save(user1);

        RefreshToken token1 = new RefreshToken();
        token1.setToken("batch-token-1");
        token1.setUser(user1);
        token1.setExpiryDate(Instant.now().plusSeconds(3600));


        User user2 = new User();
        user2.setEmail("batchuser2@example.com");
        user2.setPassword("password123");
        user2 = userRepository.save(user2);

        RefreshToken token2 = new RefreshToken();
        token2.setToken("batch-token-2");
        token2.setUser(user2);
        token2.setExpiryDate(Instant.now().plusSeconds(3600));

        refreshTokenRepository.save(token1);
        refreshTokenRepository.save(token2);

        refreshTokenRepository.deleteAllInBatch();

        assertThat(refreshTokenRepository.findAll()).isEmpty();
    }
}