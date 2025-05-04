package kg.alatoo.taskplatform.service;

import kg.alatoo.taskplatform.dto.user.UserRequest;
import kg.alatoo.taskplatform.dto.user.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAll();
    UserResponse findByEmail(String email);
    void updateByEmail(String email, UserRequest userRequest);
    void deleteByEmail(String email);
    void register(UserRequest userRequest);
}