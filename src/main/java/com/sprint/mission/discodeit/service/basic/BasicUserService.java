package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.common.UtilMethod;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BasicUserService implements UserService {
    private final UserRepository userRepository;

    public BasicUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String name, String nickname, String email, String password, String profileImageUrl) {
        User user = User.of(name, nickname, email, password, profileImageUrl, true);

        return userRepository.saveUser(user);
    }

    @Override
    public User findUserByIdOrThrow(UUID id) {
        return Optional.ofNullable(userRepository.findUserById(id))
                .orElseThrow(() -> new RuntimeException(ErrorMessage.USER_NOT_FOUND.getMessage()));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    @Override
    public User updateUser(UUID id, String name, String nickname, String email, String password, String profileImageUrl) {
        User foundUser = findUserByIdOrThrow(id);

        foundUser.updateName(name);
        foundUser.updateNickname(nickname);
        foundUser.updateEmail(email);
        foundUser.updatePassword(password);
        foundUser.updateProfileImageUrl(profileImageUrl);
        foundUser.updateUpdatedAt(UtilMethod.getCurrentTime());

        return userRepository.saveUser(foundUser);
    }

    @Override
    public void deleteUser(UUID id) {
        if (!userRepository.existsUser(id)) {
            throw new RuntimeException(ErrorMessage.USER_NOT_FOUND.getMessage());
        }
        userRepository.removeUser(id);
    }
}
