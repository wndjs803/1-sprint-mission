package com.sprint.mission.discodeit.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.jpa.user.UserJpaRepository;
import com.sprint.mission.discodeit.repository.jpa.user.UserRepositoryImpl;
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

}
