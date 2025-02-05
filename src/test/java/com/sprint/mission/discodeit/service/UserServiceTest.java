package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.common.MultipartFileConverter;
import com.sprint.mission.discodeit.dto.user.request.CreateUserRequest;
import com.sprint.mission.discodeit.dto.user.request.UpdateUserRequest;
import com.sprint.mission.discodeit.dto.user.response.CreateUserResponse;
import com.sprint.mission.discodeit.dto.user.response.FindUserResponse;
import com.sprint.mission.discodeit.dto.user.response.UpdateUserResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.user.UserMapper;
import com.sprint.mission.discodeit.repository.jcf.JCFBinaryContentRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserStatusRepository;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import com.sprint.mission.discodeit.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceTest {
    private JCFUserRepository userRepository;
    private JCFUserStatusRepository userStatusRepository;
    private JCFBinaryContentRepository binaryContentRepository;
    private UserMapper userMapper;
    private UserValidator userValidator;
    private MultipartFileConverter multipartFileConverter;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = new JCFUserRepository();
        userStatusRepository = new JCFUserStatusRepository();
        binaryContentRepository = new JCFBinaryContentRepository();
        userMapper = new UserMapper();
        userValidator = new UserValidator(userRepository);
        multipartFileConverter = new MultipartFileConverter();
        userService = new BasicUserService(userRepository, userStatusRepository, binaryContentRepository,
                userMapper, userValidator, multipartFileConverter);
    }

    private CreateUserResponse createUser(int num) {
        CreateUserRequest createUserRequest =
                new CreateUserRequest("test" + num, "nickname" + num,
                        "email" + num, "password" + num);

        return userService.createUser(createUserRequest, null);
    }

    @Nested
    @DisplayName("유저 생성 테스트")
    class createUserTest {
        @Test
        @DisplayName("유저 생성 성공")
        void success() {
            // given
            CreateUserRequest createUserRequest =
                    new CreateUserRequest("test1", "nickname1", "email1", "password1");

            // when
            CreateUserResponse response = userService.createUser(createUserRequest, null);

            // then
            assertEquals("test1", response.name());
            assertEquals("nickname1", response.nickname());
            assertEquals("email1", response.email());
            assertEquals("password1", response.password());
        }
    }

    @Nested
    @DisplayName("유저 단일 조회 테스트")
    class findUserByIdOrThrowTest {
        @Test
        @DisplayName("유저 단일 조회 성공")
        void success() {
            // given
            CreateUserResponse createUserResponse = createUser(0);

            // when
            FindUserResponse findUserResponse = userService.findUserByIdOrThrow(createUserResponse.userId());

            // then
            assertEquals(createUserResponse.name(), findUserResponse.name());
        }

        @Test
        @DisplayName("잘못된 유저 아이디 조회")
        void invalidUserId() {
            // given
            createUser(0);

            // when & then
            UUID randomId = UUID.randomUUID();
            assertThatThrownBy(() -> userService.findUserByIdOrThrow(randomId))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage(ErrorMessage.USER_NOT_FOUND.format("id: " + randomId));
        }
    }

    @Nested
    @DisplayName("유저 목록 조회 테스트")
    class findAllUsersTest {
        @Test
        @DisplayName("유저 목록 조회 성공")
        void success() {
            // given
            int size = 4;
            for (int i = 0; i < size; i++) {
                createUser(i);
            }

            // when
            List<FindUserResponse> findUserResponseList = userService.findAllUsers();

            // then
            assertEquals(4, findUserResponseList.size());
        }
    }

    @Nested
    @DisplayName("유저 수정 테스트")
    class updateUserTest {
        @Test
        @DisplayName("유저 수정 성공")
        void success() {
            // given
            CreateUserResponse createUserResponse = createUser(0);
            UpdateUserRequest updateUserRequest = new UpdateUserRequest(createUserResponse.userId(), "test2",
                    "nickname2", "email2", "password2");

            // when
            UpdateUserResponse updateUserResponse = userService.updateUser(updateUserRequest, null);

            // then
            assertEquals("test2", updateUserResponse.name());
            assertEquals("nickname2", updateUserResponse.nickname());
            assertEquals("email2", updateUserResponse.email());
            assertEquals("password2", updateUserResponse.password());
        }
    }

    @Nested
    @DisplayName("유저 삭제 테스트")
    class deleteUserTest {
        @Test
        @DisplayName("유저 삭제 성공")
        void success() {
            // given
            CreateUserResponse createUserResponse = createUser(0);
            User user = userValidator.validateUserExistsByUserId(createUserResponse.userId());
            UserStatus userStatus = userStatusRepository.findUserStatusByUser(user);
            BinaryContent profileImage = user.getProfileImage();

            // when
            userService.deleteUser(createUserResponse.userId());

            // then
            assertThat(userRepository.findUserById(user.getId())).isNull();
            assertThat(userStatusRepository.findUserStatusById(userStatus.getId())).isNull();
            assertThat(binaryContentRepository.findBinaryContentById(profileImage.getId())).isNull();
        }

        @Test
        @DisplayName("존재하지 않는 유저 삭제")
        void notExistUser() {
            UUID randomId = UUID.randomUUID();
            assertThatThrownBy(() -> userService.deleteUser(randomId))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage(ErrorMessage.USER_NOT_FOUND.format("id: " + randomId));
        }
    }
}