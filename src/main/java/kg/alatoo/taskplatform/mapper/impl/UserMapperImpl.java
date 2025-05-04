package kg.alatoo.taskplatform.mapper.impl;

import kg.alatoo.taskplatform.dto.user.UserResponse;
import kg.alatoo.taskplatform.entities.User;
import kg.alatoo.taskplatform.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserResponse toDto(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        return userResponse;
    }

    @Override
    public List<UserResponse> toDtoS(List<User> all) {
        List<UserResponse> userResponses = new ArrayList<>();
        for(User user : all) {
            userResponses.add(toDto(user));
        }
        return userResponses;
    }
}