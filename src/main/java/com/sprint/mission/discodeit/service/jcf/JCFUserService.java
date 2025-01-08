package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.ErrorMessage;
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
        // 비밀 번호 암호화
        data.put(newUser.getId(), newUser);
    }

    @Override
    public User findUserById(UUID id) {
        return Optional.of(data.get(id)).orElseThrow(() -> new RuntimeException(ErrorMessage.USER_NOT_FOUND));
    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>) data.values();
    }

    @Override
    public void updateUser(UUID id, String name, String nickname, String email, String password, String profileImageUrl) {
        User findUser = findUserById(id);

        findUser.updateName(name);
        findUser.updateNickname(nickname);
        findUser.updateNickname(email);
        findUser.updatePassword(password); // 추후 비밀 번호 암호화
        findUser.updateProfileImageUrl(profileImageUrl);

        data.put(findUser.getId(), findUser);
    }

    @Override
    public void deleteUser(UUID id) {
        if(!data.containsKey(id)){
            throw new RuntimeException(ErrorMessage.USER_NOT_FOUND);
        }
        data.remove(id);
    }
}
