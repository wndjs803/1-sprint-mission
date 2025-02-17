package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.user.request.CreateUserRequest;
import com.sprint.mission.discodeit.dto.user.request.UpdateUserRequest;
import com.sprint.mission.discodeit.dto.user.response.FindUserResponse;
import com.sprint.mission.discodeit.dto.userStatus.request.UpdateUserStatusByUserIdRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserStatusService userStatusService;

    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestPart(value = "createUserRequest") CreateUserRequest createUserRequest,
                                           @RequestPart(value = "profileImage") MultipartFile profileImage) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(createUserRequest, profileImage));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id,
                                           @RequestPart(value = "updateUserRequest") UpdateUserRequest updateUserRequest,
                                           @RequestPart(value = "profileImage") MultipartFile profileImage) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateUser(id, updateUserRequest, profileImage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("success");
    }

    @GetMapping("")
    public ResponseEntity<List<FindUserResponse>> findAllUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findAllUsers());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Boolean> updateUserStatus(@PathVariable UUID id,
                                                   @RequestBody UpdateUserStatusByUserIdRequest updateUserStatusByUserIdRequest) {
        UserStatus userStatus = userStatusService.updateUserStatusByUserId(id, updateUserStatusByUserIdRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userStatus.getIsOnline());
    }
}
