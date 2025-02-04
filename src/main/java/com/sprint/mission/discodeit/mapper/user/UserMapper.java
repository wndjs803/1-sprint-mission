package com.sprint.mission.discodeit.mapper.user;

import com.sprint.mission.discodeit.dto.user.request.CreateUserRequest;
import com.sprint.mission.discodeit.dto.user.response.CreateUserResponse;
import com.sprint.mission.discodeit.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(CreateUserRequest createUserRequest) {
        return User.of(createUserRequest.name(), createUserRequest.nickname(), createUserRequest.email(),
                createUserRequest.password(), true);
    }

    public CreateUserResponse toCreateUserResponse(User user) {
        return new CreateUserResponse(user.getId(), user.getName(), user.getNickname(), user.getEmail(), user.getPassword());
    }
}
