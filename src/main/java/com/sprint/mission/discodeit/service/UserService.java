package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void createUser(String name, String nickname, String email, String password, String profileImageUrl);
    User findUserById(UUID id);
    List<User> findAllUsers();
    void updateUser(UUID id, User user);
    void deleteUser(UUID id);
}
