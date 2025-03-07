package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.binaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.dto.user.request.CreateUserRequest;
import com.sprint.mission.discodeit.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private final BinaryContentMapper binaryContentMapper;

  public User toEntity(CreateUserRequest createUserRequest) {
    return User.of(createUserRequest.username(), createUserRequest.email(),
        createUserRequest.password());
  }

  public UserDto toUserDto(User user) {
    BinaryContentDto binaryContentDto = null;
    if (user.getProfileImage() != null) {
      binaryContentDto = binaryContentMapper.toBinaryContentDto(user.getProfileImage());
    }
    return new UserDto(user.getId(), user.getName(), user.getEmail(), binaryContentDto,
        user.getUserStatus().isRecentLogin());
  }
}
