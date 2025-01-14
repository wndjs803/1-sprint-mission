package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.common.UtilMethod;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileUserService implements UserService {
    private final FileStorage fileStorage;
    private final Path directory = Paths.get(System.getProperty("user.dir"), "data", "user");

    private FileUserService(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
        fileStorage.init(directory);
    }

    public static FileUserService getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final FileUserService INSTANCE = new FileUserService(new FileStorage());
    }

    @Override
    public User createUser(String name, String nickname, String email, String password, String profileImageUrl) {
        User user = User.of(name, nickname, email, password, profileImageUrl, true);
        Path filePath = directory.resolve(user.getId().toString().concat(".ser"));
        fileStorage.save(filePath, user);

        return user;
    }

    @Override
    public User findUserByIdOrThrow(UUID id) {
        List<User> userList = fileStorage.load(directory);

        Optional<User> optionalUser =  userList.stream()
                .filter(user -> user.getId().equals(id))
                .findAny();

        return optionalUser.orElseThrow(() -> new RuntimeException(ErrorMessage.USER_NOT_FOUND.getMessage()));
    }

    @Override
    public List<User> findAllUsers() {
        return fileStorage.load(directory);
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

        Path filePath = directory.resolve(foundUser.getId().toString().concat(".ser"));
        fileStorage.save(filePath, foundUser);

        return foundUser; // 임시 방편
    }

    @Override
    public void deleteUser(UUID id) {
        User user = findUserByIdOrThrow(id);
        fileStorage.remove(directory, user);
    }
}
