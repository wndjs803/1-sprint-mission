package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.jpa.user.UserJpaRepository;
import com.sprint.mission.discodeit.repository.jpa.user.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
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

//  @BeforeEach
//  void setUp() {
//    userRepository = new UserRepositoryImpl(userJpaRepository);
//  }

  @Test
  void createUserTest() {
    // given
    User user = User.of("name1", "email", "test1");

    // when
    User savedUser = userRepository.saveUser(user);

    // then
    Assertions.assertNotNull(savedUser);
  }
}
