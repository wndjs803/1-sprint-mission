package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserStatusRepository;
import com.sprint.mission.discodeit.service.basic.BasicUserStatusService;
import com.sprint.mission.discodeit.validator.UserStatusValidator;
import com.sprint.mission.discodeit.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserStatusServiceTest {

    private UserStatusRepository userStatusRepository;
    private UserRepository userRepository;
    private UserStatusValidator userStatusValidator;
    private UserValidator userValidator;
    private UserStatusService userStatusService;

    @BeforeEach
    void setUp() {
        userStatusRepository = new JCFUserStatusRepository();
        userStatusValidator = new UserStatusValidator(userStatusRepository);
        userRepository = new JCFUserRepository();
        userValidator = new UserValidator(userRepository);
        userStatusService = new BasicUserStatusService(userStatusRepository, userStatusValidator, userValidator);
    }

    private User createUser(int num) {
        User user = User.of("test" + num, "nickname" + num, "email" + num, "password" + num);
        return userRepository.saveUser(user);
    }

    @Nested
    @DisplayName("UserStatus 생성 테스트")
    class CreateUserStatusTest {

        @Test
        @DisplayName("UserStatus 생성 성공")
        void success() {
            // given
            User user = createUser(0);

            // when
            UserStatus userStatus = userStatusService.createUserStatus(user.getId());

            // then
            assertNotNull(userStatusRepository.findUserStatusByUser(user));
            assertNotNull(userStatusRepository.findUserStatusById(userStatus.getId()));
        }
    }
}