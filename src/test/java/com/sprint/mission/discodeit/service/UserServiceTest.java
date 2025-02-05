package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.file.FileUserService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class UserServiceTest {
    private UserService userService;
        private JCFUserRepository userRepository;
//    private FileUserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(JCFUserRepository.class);
        this.userService = new JCFUserService(userRepository);
//        userRepository = mock(FileUserRepository.class);
//        this.userService = new FileUserService(userRepository);
    }


    @Nested
    @DisplayName("유저 생성 테스트")
    class createUserTest {
        @Test
        @DisplayName("유저 생성 성공")
        void success() {
            // given
            User user = User.of("test1", "nickname1", "email1",
                    "password1", "profileImageUrl1", true);
            when(userRepository.saveUser(any())).thenReturn(user);

            // when
            User createdUser = userService.createUser("test1", "nickname1", "email1",
                    "password1", "profileImageUrl1");

            // then
            assertEquals("test1", createdUser.getName());
        }
    }

    @Nested
    @DisplayName("유저 단일 조회 테스트")
    class findUserByIdOrThrowTest {
        @Test
        @DisplayName("유저 단일 조회 성공")
        void success() {
            // given
            User user = User.of("test1", "nickname1", "email1",
                    "password1", "profileImageUrl1", true);
            when(userRepository.findUserById(any())).thenReturn(user);

            // when
            User foundUser = userService.findUserByIdOrThrow(user.getId());

            // then
            assertEquals(user.getId(), foundUser.getId());
        }

        @Test
        @DisplayName("잘못된 유저 아이디 조회")
        void invalidUserId() {
            when(userRepository.findUserById(any())).thenReturn(null);

            UUID randomId = UUID.randomUUID();
            assertThatThrownBy(() -> userService.findUserByIdOrThrow(randomId))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage(ErrorMessage.USER_NOT_FOUND.format(randomId));
        }
    }

    @Nested
    @DisplayName("유저 목록 조회 테스트")
    class findAllUsersTest {
        @Test
        @DisplayName("유저 목록 조회 성공")
        void success() {
            // given
            User user1 = User.of("test1", "nickname1", "email1",
                    "password1", "profileImageUrl1", true);
            User user2 = User.of("test2", "nickname2", "email2",
                    "password2", "profileImageUrl2", true);
            User user3 = User.of("test3", "nickname3", "email3",
                    "password3", "profileImageUrl3", true);
            User user4 = User.of("test4", "nickname4", "email4",
                    "password4", "profileImageUrl4", true);

            List<User> userList = new ArrayList<>();
            userList.add(user1);
            userList.add(user2);
            userList.add(user3);
            userList.add(user4);

            when(userRepository.findAllUsers()).thenReturn(userList);

            // when
            List<User> foundUserList = userService.findAllUsers();

            // then
            assertThat(userList).hasSameElementsAs(foundUserList);
        }
    }

    @Nested
    @DisplayName("유저 수정 테스트")
    class updateUserTest {
        @Test
        @DisplayName("유저 수정 성공")
        void success() {
            // given
            User user1 = User.of("test1", "nickname1", "email1",
                    "password1", "profileImageUrl1", true);
            when(userRepository.findUserById(any())).thenReturn(user1);
            when(userRepository.saveUser(any())).thenReturn(user1);

            // when
            User updatedUser = userService.updateUser(user1.getId(), "test2",
                    "nickname2", "email2", "password2", "profileImageUrl2");

            // then
            assertEquals("test2", updatedUser.getName());
            assertEquals("nickname2", updatedUser.getNickname());
            assertEquals("email2", updatedUser.getEmail());
            assertEquals("password2", updatedUser.getPassword());
            assertEquals("profileImageUrl2", updatedUser.getProfileImageUrl());
        }
    }

    @Nested
    @DisplayName("유저 삭제 테스트")
    class deleteUserTest {
        @Test
        @DisplayName("유저 삭제 성공")
        void success() {
            // given
            User user = User.of("test1", "nickname1", "email1",
                    "password1", "profileImageUrl1", true);
            when(userRepository.existsUser(user.getId())).thenReturn(true);

            // when
            userService.deleteUser(user.getId());

            // then
            verify(userRepository).removeUser(any());
        }

        @Test
        @DisplayName("존재하지 않는 유저 삭제")
        void notExistUser() {
            UUID randomId = UUID.randomUUID();
            assertThatThrownBy(() -> userService.deleteUser(randomId))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage(ErrorMessage.USER_NOT_FOUND.format(randomId));
        }
    }
}