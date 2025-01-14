package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class FileUserRepository implements UserRepository {
    private final FileStorage fileStorage;
    private final Path directory = Paths.get(System.getProperty("user.dir"), "data", "user");

    private FileUserRepository(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
        fileStorage.init(directory);
    }

    public static FileUserRepository getInstance(){
        return FileUserRepository.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final FileUserRepository INSTANCE = new FileUserRepository(new FileStorage());
    }
    @Override
    public User saveUser(User user) {
        Path filePath = directory.resolve(user.getId().toString().concat(".ser"));
        return fileStorage.save(filePath, user);
    }

    @Override
    public User findUserById(UUID userId) {
        List<User> userList = fileStorage.load(directory);

        return userList.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst().get();
    }

    @Override
    public List<User> findAllUsers() {
        return fileStorage.load(directory);
    }

    @Override
    public void removeUser(UUID userId) {
        User user = findUserById(userId);
        fileStorage.remove(directory, user);
    }

    @Override
    public boolean existsUser(UUID userId) {
        List<User> userList = fileStorage.load(directory);

        return userList.stream()
                .anyMatch(user -> user.getId().equals(userId));
    }
}
