package com.sprint.mission.discodeit.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.jpa.user.UserJpaRepository;
import com.sprint.mission.discodeit.repository.jpa.user.UserRepositoryImpl;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(UserRepositoryImpl.class)
public class UserRepositoryTest {

  @Autowired
  UserJpaRepository userJpaRepository;

  @Autowired
  UserRepositoryImpl userRepository;

  private UUID saveUser() {
    User user = User.of("name2", "email2", "password2");
    return userRepository.saveUser(user).getId();
  }

  @Nested
  class SaveUserTest {

    @Test
    void 유저_저장_성공() {
      // given
      User user = User.of("name1", "email", "password1");

      // when
      User savedUser = userRepository.saveUser(user);

      // then
      assertNotNull(savedUser);
      assertEquals("name1", savedUser.getName());
      assertEquals("email", savedUser.getEmail());
      assertEquals("password1", savedUser.getPassword());
    }
  }

  @Nested
  class FindUserByIdTest {

    @Test
    void 유저_아이디로_조회_성공() {
      // given
      UUID userId = saveUser();

      // when
      Optional<User> userOptional = userRepository.findUserById(userId);

      // then
      assertTrue(userOptional.isPresent());

      User user = userOptional.get();
      assertEquals("name2", user.getName());
    }

    @Test
    void 존재하지_않는_유저_아이디로_조회_() {
      // given
      UUID userId = UUID.randomUUID();

      // when
      Optional<User> userOptional = userRepository.findUserById(userId);

      // then
      assertFalse(userOptional.isPresent());
    }
  }
}
