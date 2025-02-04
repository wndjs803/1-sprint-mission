package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository("jcfUserRepository")
@NoArgsConstructor
public class JCFUserRepository implements UserRepository {
    private final Map<UUID, User> userData = new HashMap<>();

    @Override
    public User saveUser(User user) {
        userData.put(user.getId(), user);
        return userData.get(user.getId());
    }

    @Override
    public User findUserById(UUID userId) {
        return userData.get(userId);
    }

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(userData.values());
    }

    @Override
    public void removeUser(UUID userId) {
        userData.remove(userId);
    }

    @Override
    public boolean existsUser(UUID userId) {
        return userData.containsKey(userId);
    }
}
