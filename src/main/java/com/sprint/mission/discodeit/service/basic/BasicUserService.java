package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.common.MultipartFileConverter;
import com.sprint.mission.discodeit.common.UtilMethod;
import com.sprint.mission.discodeit.dto.user.request.CreateUserRequest;
import com.sprint.mission.discodeit.dto.user.response.CreateUserResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.user.UserMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {

//    @Qualifier("fileUserRepository")
    @Qualifier("jcfUserRepository")
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final UserMapper userMapper;
    private final MultipartFileConverter multipartFileConverter;

    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest, MultipartFile profileImageFile) {

        // name 중복 여부
        if (userRepository.findUserByName(createUserRequest.name()) != null) {
            throw new RuntimeException(ErrorMessage.USER_ALREADY_EXIST.format("name: " + createUserRequest.name()));
        }

        // email 중복 여부
        if (userRepository.findUserByEmail(createUserRequest.email()) != null) {
            throw new RuntimeException(ErrorMessage.USER_ALREADY_EXIST.format("email: " + createUserRequest.email()));
        }

        User user = userMapper.toEntity(createUserRequest);

        // 이미지는 선택적으로 등록
        if (profileImageFile != null) {
            user.updateProfileImage(BinaryContent.of(multipartFileConverter.toByteArray(profileImageFile)));
        }

        // UserStatus 생성
        userStatusRepository.saveUserStatus(UserStatus.of(user));

        return userMapper.toCreateUserResponse(userRepository.saveUser(user));
    }

    @Override
    public User findUserByIdOrThrow(UUID userId) {
        return Optional.ofNullable(userRepository.findUserById(userId))
                .orElseThrow(() -> new RuntimeException(ErrorMessage.USER_NOT_FOUND.format(userId)));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    @Override
    public User updateUser(UUID userId, String name, String nickname, String email, String password, String profileImageUrl) {
        User foundUser = findUserByIdOrThrow(userId);

        foundUser.updateName(name);
        foundUser.updateNickname(nickname);
        foundUser.updateEmail(email);
        foundUser.updatePassword(password);
//        foundUser.updateProfileImageUrl(profileImageUrl);
        foundUser.updateUpdatedAt(UtilMethod.getCurrentTime());

        return userRepository.saveUser(foundUser);
    }

    @Override
    public void deleteUser(UUID userId) {
        if (!userRepository.existsUser(userId)) {
            throw new RuntimeException(ErrorMessage.USER_NOT_FOUND.format(userId));
        }
        userRepository.removeUser(userId);
    }
}
