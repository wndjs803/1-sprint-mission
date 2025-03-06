package com.sprint.mission.discodeit.repository.jpa.user;

import com.sprint.mission.discodeit.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, UUID> {

  Optional<User> findUserByName(String name);

  Optional<User> findUserByEmail(String email);

  Optional<User> findUserByNameAndPassword(String name, String password);
}
