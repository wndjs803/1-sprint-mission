package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.List;
import java.util.UUID;

public interface UserStatusService {
    UserStatus createUserStatus(UUID userId);

    UserStatus findUserStatusById(UUID userStatusId);

    List<UserStatus> findAllUserStatuses();

    UserStatus updateUserStatusById(UUID userStatusId);

    UserStatus updateUserStatusByUserId(UUID userId);

    void deleteUserStatus(UUID userStatusId);
}
