package com.sprint.mission.discodeit.validator;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserStatusValidator {

    private final UserStatusRepository userStatusRepository;

    public UserStatus validateUserStatusExistsById(UUID userStatusId) {
        return Optional.ofNullable(userStatusRepository.findUserStatusById(userStatusId))
                .orElseThrow(() -> new IllegalArgumentException(
                        ErrorMessage.USERSTATUS_NOT_FOUND.format("id: " + userStatusId)));
    }

    public UserStatus validateUserStatusExistsByUser(User user) {
        return userStatusRepository.findUserStatusByUser(user)
                .orElseThrow(() -> new IllegalArgumentException(
                        ErrorMessage.USERSTATUS_NOT_FOUND.format("id: " + user.getId())));
    }

    public void validateDuplicateByUser(User user) {
        if (userStatusRepository.findUserStatusByUser(user).isPresent()) {
            throw new RuntimeException(ErrorMessage.USERSTATUS_ALREADY_EXIST.format("userId: " + user.getId()));
        }
    }
}
