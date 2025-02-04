package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    User saveUser(User user);

    User findUserById(UUID userId);

    List<User> findAllUsers();

    void removeUser(UUID userId);

    boolean existsUser(UUID userId);

    User findUserByName(String name);

    User findUserByEmail(String email);

}
