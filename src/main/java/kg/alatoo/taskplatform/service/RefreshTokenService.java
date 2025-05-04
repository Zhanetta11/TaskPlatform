package kg.alatoo.taskplatform.service;

import kg.alatoo.taskplatform.entities.RefreshToken;
import kg.alatoo.taskplatform.entities.User;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(User user);
    boolean validateRefreshToken(String token);
    void deleteByUser(User user);
}