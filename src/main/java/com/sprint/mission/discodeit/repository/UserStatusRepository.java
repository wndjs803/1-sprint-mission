package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.List;
import java.util.UUID;

public interface UserStatusRepository {
    UserStatus saveUserStatus(UserStatus userStatus);
    UserStatus findUserStatusById(UUID userStatusId);
    List<UserStatus> findAllUserStatuses();
    void removeUserStatus(UUID userStatusId);
}
