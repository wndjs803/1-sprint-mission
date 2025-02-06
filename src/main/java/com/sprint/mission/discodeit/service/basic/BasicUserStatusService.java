package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import com.sprint.mission.discodeit.validator.UserStatusValidator;
import com.sprint.mission.discodeit.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {

    private final UserStatusRepository userStatusRepository;
    private final UserStatusValidator userStatusValidator;
    private final UserValidator userValidator;

    @Override
    public UserStatus createUserStatus(UUID userId) {
        User user = userValidator.validateUserExistsByUserId(userId);

        userStatusValidator.validateDuplicateByUser(user);

        return userStatusRepository.saveUserStatus(UserStatus.of(user));
    }

    @Override
    public UserStatus findUserStatusById(UUID userStatusId) {
        return userStatusValidator.validateUserStatusExistsById(userStatusId);
    }

    @Override
    public List<UserStatus> findAllUserStatuses() {
        return userStatusRepository.findAllUserStatuses();
    }

    @Override
    public UserStatus updateUserStatusById(UUID userStatusId) {
        UserStatus userStatus = userStatusValidator.validateUserStatusExistsById(userStatusId);

        userStatus.updateUserStatusInfo();

        return userStatusRepository.saveUserStatus(userStatus);
    }

    @Override
    public UserStatus updateUserStatusByUserId(UUID userId) {
        User user = userValidator.validateUserExistsByUserId(userId);
        UserStatus userStatus = userStatusValidator.validateUserStatusExistsByUser(user);

        userStatus.updateUserStatusInfo();

        return userStatusRepository.saveUserStatus(userStatus);
    }

    @Override
    public void deleteUserStatus(UUID userStatusId) {
        userStatusValidator.validateUserStatusExistsById(userStatusId);

        userStatusRepository.removeUserStatus(userStatusId);
    }
}
