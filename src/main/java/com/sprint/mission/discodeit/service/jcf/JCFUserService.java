package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.Main;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class JCFUserService implements UserService {
    private final Map<UUID, User> data;

    public JCFUserService() {
        this.data = new HashMap<>();
    }

    @Override
    public void createUser(String name, String nickname, String email, String password,
                           String profileImageUrl) {
        User newUser = new User(name, nickname, email, password, profileImageUrl, true, new ArrayList<>());
        data.put(newUser.getId(), newUser);
    }

    @Override
    public User findUserById(UUID id) {
        return Optional.of(data.get(id)).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>) data.values();
    }

    @Override
    public void updateUser(UUID id, User user) {

    }

    @Override
    public void deleteUser(UUID id) {

    }
}
