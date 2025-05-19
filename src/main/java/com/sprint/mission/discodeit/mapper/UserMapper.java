package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.dto.user.request.CreateUserRequest;
import com.sprint.mission.discodeit.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BinaryContentMapper.class})
public interface UserMapper {

    @Mapping(target = "name", source = "createUserRequest.username")
    @Mapping(target = "profileImage", ignore = true)
    @Mapping(target = "userStatus", ignore = true)
    @Mapping(target = "password", source = "hashedPassword")
    User toEntity(CreateUserRequest createUserRequest, String hashedPassword);

    @Mapping(target = "username", source = "name")
    @Mapping(target = "profile", source = "profileImage")
    @Mapping(target = "online", expression = "java(user.getUserStatus().isRecentLogin())")
    UserDto toUserDto(User user);
}
