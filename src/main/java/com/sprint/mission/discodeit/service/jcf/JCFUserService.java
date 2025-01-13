package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.common.UtilMethod;
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

    private JCFUserService() {
        this.userData = new HashMap<>();
    }

    public static JCFUserService getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final JCFUserService INSTANCE = new JCFUserService();
    }

    @Override
    public User createUser(String name, String nickname, String email, String password,
                           String profileImageUrl) {
        // 추후 중복 검사
        User user = new User(name, nickname, email, password, profileImageUrl, true);
        // 비밀 번호 암호화
        userData.put(user.getId(), user);

        return user;
    }

    @Override
    public User findUserByIdOrThrow(UUID id) {
        return Optional.ofNullable(userData.get(id)).orElseThrow(() -> new RuntimeException(ErrorMessage.USER_NOT_FOUND));
    }

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(userData.values());
    }

    @Override
    public void updateUser(UUID id, String name, String nickname, String email, String password, String profileImageUrl) {
        User foundUser = findUserByIdOrThrow(id);

        foundUser.updateName(name);
        foundUser.updateNickname(nickname);
        foundUser.updateEmail(email);
        foundUser.updatePassword(password); // 추후 비밀 번호 암호화
        foundUser.updateProfileImageUrl(profileImageUrl);
        foundUser.updateUpdatedAt(UtilMethod.getCurrentTime());

        userData.put(foundUser.getId(), foundUser);
    }

    @Override
    public void deleteUser(UUID id) {
        if(!userData.containsKey(id)){
            throw new RuntimeException(ErrorMessage.USER_NOT_FOUND);
        }
        userData.remove(id);
    }
}
