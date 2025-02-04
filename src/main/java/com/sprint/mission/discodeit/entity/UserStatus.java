package com.sprint.mission.discodeit.entity;

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
        return new UserStatus(user);
    }

    public void updateLoginAt(Instant loginAt) {
        this.loginAt = loginAt;
    }

    public void updateOnline() {
        this.isOnline = ChronoUnit.MINUTES.between(Instant.now(), this.loginAt) < 5;
    }
}
