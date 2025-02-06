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

    public void updateLoginAt(Instant loginAt) {
        this.loginAt = loginAt;
        this.updateUpdatedAt(TimeUtil.getCurrentTime());
    }

    public void updateOnline() {
        this.isOnline = ChronoUnit.MINUTES.between(Instant.now(), this.loginAt) < 5;
        this.updateUpdatedAt(TimeUtil.getCurrentTime());
    }
}
