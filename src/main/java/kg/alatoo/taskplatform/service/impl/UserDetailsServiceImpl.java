package kg.alatoo.taskplatform.service.impl;

import kg.alatoo.taskplatform.dto.user.UserRequest;
import kg.alatoo.taskplatform.dto.user.UserResponse;
import kg.alatoo.taskplatform.entities.User;
import kg.alatoo.taskplatform.mapper.UserMapper;
import kg.alatoo.taskplatform.repositories.UserRepository;
import kg.alatoo.taskplatform.service.UserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponse> getAll() {
        return userMapper.toDtoS(userRepository.findAll());
    }

    @Override
    public UserResponse findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return userMapper.toDto(user.get());
    }

    @Override
    public void updateByEmail(String email, UserRequest userRequest) {
        Optional<User> user = userRepository.findByEmail(email);

        user.get().setName(userRequest.getName());
        user.get().setEmail(userRequest.getEmail());
        user.get().setPassword(userRequest.getPassword());
        userRepository.save(user.get());
    }

    @Override
    public void deleteByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        userRepository.deleteByEmail(email);
    }

    @Override
    public void register(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        userRepository.save(user);
    }
}