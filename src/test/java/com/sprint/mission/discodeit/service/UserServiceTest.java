package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class UserServiceTest {
    private final UserService userService = JCFUserService.getInstance();

    @Nested
    @DisplayName("유저 생성 테스트")
    class createUserTest{
        @Test
        @DisplayName("유저 생성 성공")
        void success() {
            User user = userService.createUser("test1", "nickname1", "email1",
                    "password1", "profileImageUrl1");

            assertEquals("test1", user.getName());
        }
    }

    @Nested
    @DisplayName("유저 단일 조회 테스트")
    class findUserByIdOrThrowTest{
        @Test
        @DisplayName("유저 단일 조회 성공")
        void success(){
            User user = userService.createUser("test1", "nickname1", "email1",
                    "password1", "profileImageUrl1");

            User foundUser = userService.findUserByIdOrThrow(user.getId());

            assertEquals(user.getId(), foundUser.getId());
        }

        @Test
        @DisplayName("잘못된 유저 아이디 조회")
        void invalidUserId(){
            assertThrows(RuntimeException.class, ()-> userService.findUserByIdOrThrow(UUID.randomUUID()));
        }
    }

    @Nested
    @DisplayName("유저 목록 조회 테스트")
    class findAllUsersTest {
        @Test
        @DisplayName("유저 목록 조회 성")
        void success() {
            User user1 = userService.createUser("test1", "nickname1", "email1",
                    "password1", "profileImageUrl1");
            User user2 = userService.createUser("test2", "nickname2", "email2",
                    "password2", "profileImageUrl2");
            User user3 = userService.createUser("test3", "nickname3", "email3",
                    "password3", "profileImageUrl3");
            User user4 = userService.createUser("test4", "nickname4", "email4",
                    "password4", "profileImageUrl4");

            List<User> userList = new ArrayList<>();
            userList.add(user1);
            userList.add(user2);
            userList.add(user3);
            userList.add(user4);

            List<User> foundUserList = userService.findAllUsers();

            assertThat(userList).hasSameElementsAs(foundUserList);
        }
    }

    @Nested
    @DisplayName("유저 수정 테스트")
    class updateUserTest {
        @Test
        @DisplayName("유저 수정 성공")
        void success() {
            User user = userService.createUser("test1", "nickname1", "email1",
                    "password1", "profileImageUrl1");

            userService.updateUser(user.getId(), "test2", "nickname2", "email2",
                    "password2", "profileImageUrl2");

            User updatedUser = userService.findUserByIdOrThrow(user.getId());

            assertEquals("test2", updatedUser.getName());
        }
    }

    @Nested
    @DisplayName("유저 삭제 테스트")
    class deleteUserTest {
        @Test
        @DisplayName("유저 삭제 성공")
        void success() {
            User user = userService.createUser("test1", "nickname1", "email1",
                    "password1", "profileImageUrl1");
            userService.deleteUser(user.getId());

            assertThrows(RuntimeException.class, ()-> userService.findUserByIdOrThrow(user.getId()));
        }

        @Test
        @DisplayName("존재하지 않는 유저 삭제")
        void notExistUser() {
            assertThrows(RuntimeException.class, ()-> userService.deleteUser(UUID.randomUUID()));
        }
    }
}