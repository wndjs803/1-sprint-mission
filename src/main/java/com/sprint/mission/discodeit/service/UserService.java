package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User createUser(String name, String nickname, String email, String password, String profileImageUrl);

    User findUserByIdOrThrow(UUID id);

    List<User> findAllUsers();

    User updateUser(UUID id, String name, String nickname, String email, String password, String profileImageUrl);

    void deleteUser(UUID id);
}
