package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.common.TimeUtil;
import lombok.Getter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Getter
public class UserStatus extends BaseEntity {

    private final User user;
    private Instant loginAt;
    private boolean isOnline = false;

    private UserStatus(User user) {
        super();
        this.user = user;
        this.loginAt = this.getCreatedAt();
    }

    public static UserStatus of(User user) {
        if (user == null) {
            throw new IllegalArgumentException(ErrorMessage.USER_NOT_NULL.getMessage());
        }
        return new UserStatus(user);
    }

    public boolean getIsOnline() {
        return this.isOnline;
    }

    public void updateLoginAt() {
        this.loginAt = TimeUtil.getCurrentTime();
        this.updateUpdatedAt(TimeUtil.getCurrentTime());
    }

    public boolean isLoggedInWithinLast5Minutes() {
        return ChronoUnit.MINUTES.between(Instant.now(), this.loginAt) < 5;
    }

    public void updateOnline(boolean isOnline) {
        this.isOnline = isOnline;
        this.updateUpdatedAt(TimeUtil.getCurrentTime());
    }

    public void updateUserStatusInfo(boolean isOnline) {
        this.updateLoginAt();
        this.updateOnline(isOnline);
    }
}
