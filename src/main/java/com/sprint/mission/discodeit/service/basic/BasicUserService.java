package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.common.MultipartFileConverter;
import com.sprint.mission.discodeit.common.UtilMethod;
import com.sprint.mission.discodeit.dto.user.request.CreateUserRequest;
import com.sprint.mission.discodeit.dto.user.request.UpdateUserRequest;
import com.sprint.mission.discodeit.dto.user.response.CreateUserResponse;
import com.sprint.mission.discodeit.dto.user.response.FindUserResponse;
import com.sprint.mission.discodeit.dto.user.response.UpdateUserResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.user.UserMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {

//    @Qualifier("fileUserRepository")
    @Qualifier("jcfUserRepository")
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final UserMapper userMapper;
    private final MultipartFileConverter multipartFileConverter;
    private final UserValidator userValidator;

    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest, MultipartFile profileImageFile) {

        // name 중복 여부
        userValidator.validateDuplicateByName(createUserRequest.name());

        // email 중복 여부
        userValidator.validateDuplicateUserByEmail(createUserRequest.email());

        User user = userMapper.toEntity(createUserRequest);

        // 이미지는 선택적으로 등록
        updateProfileImage(user, profileImageFile);

        // UserStatus 생성(추후 service layer로 교체)
        userStatusRepository.saveUserStatus(UserStatus.of(user));

        return userMapper.toCreateUserResponse(userRepository.saveUser(user));
    }

    @Override
    public FindUserResponse findUserByIdOrThrow(UUID userId) {
        User foundUser = userValidator.validateUserExistsByUserId(userId);

        UserStatus userStatus = userStatusRepository.findUserStatusByUser(foundUser); // userStatus Service나 validator로

        MultipartFile profileImage = multipartFileConverter.toMultipartFile(foundUser.getProfileImage().getContent());

        return userMapper.toFindUserResponse(foundUser, profileImage, userStatus.getIsOnline());
    }

    @Override
    public List<FindUserResponse> findAllUsers() {
        return userRepository.findAllUsers().stream()
                .map(user -> findUserByIdOrThrow(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest, MultipartFile profileImageFile) {
        User foundUser = userValidator.validateUserExistsByUserId(updateUserRequest.userId());

        foundUser.updateName(updateUserRequest.name());
        foundUser.updateNickname(updateUserRequest.nickname());
        foundUser.updateEmail(updateUserRequest.email());
        foundUser.updatePassword(updateUserRequest.password());

        updateProfileImage(foundUser, profileImageFile);

        foundUser.updateUpdatedAt(UtilMethod.getCurrentTime());

        return userMapper.toUpdateUserResponse(foundUser);
    }

    @Override
    public void deleteUser(UUID userId) {
        if (!userRepository.existsUser(userId)) {
            throw new RuntimeException(ErrorMessage.USER_NOT_FOUND.format(userId));
        }
        userRepository.removeUser(userId);
    }

    private void updateProfileImage(User user, MultipartFile profileImageFile) {
        if (profileImageFile != null) {
            user.updateProfileImage(BinaryContent.of(multipartFileConverter.toByteArray(profileImageFile)));
        }
    }
}
