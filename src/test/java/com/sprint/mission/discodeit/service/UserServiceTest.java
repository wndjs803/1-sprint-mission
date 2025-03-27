package com.sprint.mission.discodeit.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sprint.mission.discodeit.common.error.ErrorCode;
import com.sprint.mission.discodeit.common.error.execption.user.UserAlreadyExistException;
import com.sprint.mission.discodeit.common.error.execption.user.UserNotFoundException;
import com.sprint.mission.discodeit.common.util.MultipartFileConverter;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.dto.user.request.CreateUserRequest;
import com.sprint.mission.discodeit.dto.user.request.UpdateUserRequest;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.repository.file.FileStorage;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFBinaryContentRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserStatusRepository;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import com.sprint.mission.discodeit.validator.UserStatusValidator;
import com.sprint.mission.discodeit.validator.UserValidator;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UserServiceTest {

  private UserRepository userRepository;
  private UserStatusRepository userStatusRepository;
  private BinaryContentRepository binaryContentRepository;
  private UserMapper userMapper;
  private UserValidator userValidator;
  private UserStatusValidator userStatusValidator;
  private MultipartFileConverter multipartFileConverter;
  private UserService userService;
  private FileStorage fileStorage;

  @BeforeEach
  void setUp() {
    jcfSetUp();
//        fileSetUp();
    userMapper = new UserMapper();
    userValidator = new UserValidator(userRepository);
    userStatusValidator = new UserStatusValidator(userStatusRepository);
    multipartFileConverter = new MultipartFileConverter();
    userService = new BasicUserService(userRepository, userStatusRepository,
        binaryContentRepository,
        userMapper, userValidator, userStatusValidator, multipartFileConverter);
  }

  @AfterEach
  void clean() {
    if (fileStorage != null) {
      fileStorage.clearDataDirectory();
    }
  }

  private void jcfSetUp() {
    userRepository = new JCFUserRepository();
    userStatusRepository = new JCFUserStatusRepository();
    binaryContentRepository = new JCFBinaryContentRepository();
  }

  private void fileSetUp() {
    fileStorage = new FileStorage();
    userRepository = new FileUserRepository(fileStorage);
    userStatusRepository = new JCFUserStatusRepository(); // 추후 변경
    binaryContentRepository = new JCFBinaryContentRepository(); // 추후 변경
  }

  private UserDto createUser(int num) {
    CreateUserRequest createUserRequest =
        new CreateUserRequest("test" + num,
            "email" + num, "password" + num);

    return userService.createUser(createUserRequest, null);
  }

  @Nested
  @DisplayName("유저 생성 테스트")
  class CreateUserTest {

    @Test
    @DisplayName("유저 생성 성공")
    void success() {
      // given
      CreateUserRequest createUserRequest =
          new CreateUserRequest("test1", "email1", "password1");

      // when
      UserDto user = userService.createUser(createUserRequest, null);

      // then
      assertEquals("test1", user.username());
      assertEquals("email1", user.email());
      assertEquals("password1", user.password());
    }

    @Test
    @DisplayName("유저 이름 중복")
    void duplicateByName() {
      // given
      createUser(1);

      CreateUserRequest createUserRequest =
          new CreateUserRequest("test1", "email1", "password1");

      // when & then
      assertThatThrownBy(() -> userService.createUser(createUserRequest, null))
          .isInstanceOf(UserAlreadyExistException.class)
          .hasMessage(ErrorCode.USER_ALREADY_EXIST.format("name: test1"));
    }

    @Test
    @DisplayName("유저 이메일 중복")
    void duplicateByEmail() {
      // given
      createUser(1);

      CreateUserRequest createUserRequest =
          new CreateUserRequest("test2", "email1", "password1");

      // when & then
      assertThatThrownBy(() -> userService.createUser(createUserRequest, null))
          .isInstanceOf(UserAlreadyExistException.class)
          .hasMessage(ErrorCode.USER_ALREADY_EXIST.format("email: email1"));
    }
  }

  @Nested
  @DisplayName("유저 단일 조회 테스트")
  class FindUserByIdOrThrowTest {

    @Test
    @DisplayName("유저 단일 조회 성공")
    void success() {
      // given
      UserDto user = createUser(0);

      // when
      UserDto findUserResponse = userService.findUserByIdOrThrow(user.id());

      // then
      assertEquals(user.username(), findUserResponse.username());
    }

    @Test
    @DisplayName("잘못된 유저 아이디 조회")
    void invalidUserId() {
      // given
      createUser(0);

      // when & then
      UUID randomId = UUID.randomUUID();
      assertThatThrownBy(() -> userService.findUserByIdOrThrow(randomId))
          .isInstanceOf(UserNotFoundException.class)
          .hasMessage(ErrorCode.USER_NOT_FOUND.format("id: " + randomId));
    }
  }

  @Nested
  @DisplayName("유저 목록 조회 테스트")
  class FindAllUsersTest {

    @Test
    @DisplayName("유저 목록 조회 성공")
    void success() {
      // given
      int size = 4;
      for (int i = 0; i < size; i++) {
        createUser(i);
      }

      // when
      List<UserDto> findUserResponseList = userService.findAllUsers();

      // then
      assertEquals(4, findUserResponseList.size());
    }
  }

  @Nested
  @DisplayName("유저 수정 테스트")
  class UpdateUserTest {

    @Test
    @DisplayName("유저 수정 성공")
    void success() {
      // given
      UserDto user = createUser(0);
      UpdateUserRequest updateUserRequest = new UpdateUserRequest("test2",
          "email2", "password2");

      // when
      UserDto updatedUser = userService.updateUser(user.id(), updateUserRequest, null);

      // then
      assertEquals("test2", updatedUser.username());
      assertEquals("email2", updatedUser.email());
      assertEquals("password2", updatedUser.password());
    }
  }

  @Nested
  @DisplayName("유저 삭제 테스트")
  class DeleteUserTest {

    @Test
    @DisplayName("유저 삭제 성공")
    void success() {
      // given
//            UserDto user = createUser(0);
//            UserStatus userStatus = userStatusValidator.validateUserStatusExistsByUser(user);
//            BinaryContent profileImage = user.getProfileImage();
//
//            // when
//            userService.deleteUser(user.getId());
//
//            // then
//            assertThat(userRepository.findUserById(user.getId())).isNull();
//            assertThat(userStatusRepository.findUserStatusById(userStatus.getId())).isNull();
//            assertThat(binaryContentRepository.findBinaryContentById(profileImage.getId())).isNull();
    }

    @Test
    @DisplayName("존재하지 않는 유저 삭제")
    void notExistUser() {
      UUID randomId = UUID.randomUUID();
      assertThatThrownBy(() -> userService.deleteUser(randomId))
          .isInstanceOf(UserNotFoundException.class)
          .hasMessage(ErrorCode.USER_NOT_FOUND.format("id: " + randomId));
    }
  }
}