package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.common.UtilMethod;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFUserService implements UserService {
    private final JCFUserRepository jcfUserRepository;

    private JCFUserService(JCFUserRepository jcfUserRepository) {
        this.jcfUserRepository = jcfUserRepository;
    }

    public static JCFUserService getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final JCFUserService INSTANCE = new JCFUserService(JCFUserRepository.getInstance());
    }

    @Override
    public User createUser(String name, String nickname, String email, String password,
                           String profileImageUrl) {
        // 추후 중복 검사
        User user = User.of(name, nickname, email, password, profileImageUrl, true);
        // 비밀 번호 암호화
        jcfUserRepository.saveUser(user);

        return user;
    }

    @Override
    public User findUserByIdOrThrow(UUID id) {
        return Optional.ofNullable(jcfUserRepository.findUserById(id))
                .orElseThrow(() -> new RuntimeException(ErrorMessage.USER_NOT_FOUND.getMessage()));
    }

    @Override
    public List<User> findAllUsers() {
        return jcfUserRepository.findAllUsers();
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

        jcfUserRepository.saveUser(foundUser);
    }

    @Override
    public void deleteUser(UUID id) {
        if(!jcfUserRepository.existsUser(id)){
            throw new RuntimeException(ErrorMessage.USER_NOT_FOUND.getMessage());
        }
        jcfUserRepository.removeUser(id);
    }
}
