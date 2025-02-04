package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.user.request.CreateUserRequest;
import com.sprint.mission.discodeit.dto.user.response.CreateUserResponse;
import com.sprint.mission.discodeit.dto.user.response.FindUserResponse;
import com.sprint.mission.discodeit.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest createUserRequest, MultipartFile profileImageFile);

    FindUserResponse findUserByIdOrThrow(UUID id);

    List<FindUserResponse> findAllUsers();

    User updateUser(UUID id, String name, String nickname, String email, String password, String profileImageUrl);

    void deleteUser(UUID id);
}
