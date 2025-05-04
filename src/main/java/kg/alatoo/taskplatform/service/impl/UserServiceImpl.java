package kg.alatoo.taskplatform.service.impl;

import kg.alatoo.taskplatform.service.UserService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import kg.alatoo.taskplatform.dto.user.UserRequest;
import kg.alatoo.taskplatform.dto.user.UserResponse;
import kg.alatoo.taskplatform.entities.User;
import kg.alatoo.taskplatform.exception.CustomException;
import kg.alatoo.taskplatform.mapper.UserMapper;
import kg.alatoo.taskplatform.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
@Primary
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getAll() {
        return userMapper.toDtoS(userRepository.findAll());
    }

    @Override
    public UserResponse findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        checker(user, email);
        return userMapper.toDto(user.get());
    }

    @Override
    public void updateByEmail(String email, UserRequest userRequest) {
        Optional<User> user = userRepository.findByEmail(email);
        checker(user, email);
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent() && !email.equals(userRequest.getEmail())) {
            throw new CustomException("user with email: " + userRequest.getEmail() + " already exist!", HttpStatus.CONFLICT);
        }
        user.get().setName(userRequest.getName());
        user.get().setEmail(userRequest.getEmail());
        user.get().setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(user.get());
    }

    @Override
    public void deleteByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        checker(user, email);
        userRepository.deleteByEmail(email);
    }

    @Override
    public void register(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
    }

    private void checker(Optional<User> user, String email) {
        if (user.isEmpty()) {
            throw new CustomException("User is not found with the email: " + email, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().toUpperCase())
                .build();
    }
}