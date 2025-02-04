package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Getter
public class UserStatus extends BaseEntity {

    private final User user;
    private Instant loginAt;
    private boolean isOnline = false;

    public UserStatus(User user) {
        this.user = user;
    }

    public void updateLoginAt(Instant loginAt) {
        this.loginAt = loginAt;
    }

    public void updateOnline() {
        this.isOnline = ChronoUnit.MINUTES.between(Instant.now(), this.loginAt) < 5;
    }
}
