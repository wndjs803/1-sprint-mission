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
    private final Map<UUID, User> userData;

    public JCFUserService() {
        this.userData = new HashMap<>();
    }

    @Override
    public void createUser(String name, String nickname, String email, String password,
                           String profileImageUrl) {
        // 추후 중복 검사
        User newUser = new User(name, nickname, email, password, profileImageUrl, true, new ArrayList<>());
        // 비밀 번호 암호화
        userData.put(newUser.getId(), newUser);
    }

    @Override
    public User findUserById(UUID id) {
        return Optional.of(userData.get(id)).orElseThrow(() -> new RuntimeException(ErrorMessage.USER_NOT_FOUND));
    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>) userData.values();
    }

    @Override
    public void updateUser(UUID id, String name, String nickname, String email, String password, String profileImageUrl) {
        User findUser = findUserById(id);

        findUser.updateName(name);
        findUser.updateNickname(nickname);
        findUser.updateNickname(email);
        findUser.updatePassword(password); // 추후 비밀 번호 암호화
        findUser.updateProfileImageUrl(profileImageUrl);

        userData.put(findUser.getId(), findUser);
    }

    @Override
    public void deleteUser(UUID id) {
        if(!userData.containsKey(id)){
            throw new RuntimeException(ErrorMessage.USER_NOT_FOUND);
        }
        userData.remove(id);
    }
}
