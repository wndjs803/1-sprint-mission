package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import com.sprint.mission.discodeit.global.error.ErrorCode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.Getter;

@Getter
public class UserStatus extends BaseUpdatableEntity {

  private final User user;
  private Instant loginAt;

  private static final long LOGIN_EXPIRATION_MINUTES = 5;

  private UserStatus(User user) {
    super();
    this.user = user;
    this.loginAt = this.getCreatedAt();
  }

  public static UserStatus of(User user) {
    if (user == null) {
      throw new IllegalArgumentException(ErrorCode.USER_NOT_NULL.getMessage());
    }
    return new UserStatus(user);
  }

  public void updateLoginAt(Instant newLastActiveAt) {
    this.loginAt = newLastActiveAt;
    this.updateUpdatedAt();
  }

  public boolean isRecentLogin() {
    if (this.getCreatedAt() == this.loginAt) {
      return false;
    }
    return ChronoUnit.MINUTES.between(Instant.now(), this.loginAt) < LOGIN_EXPIRATION_MINUTES;
  }

  public void updateUserStatusInfo(Instant newLastActiveAt) {
    this.updateLoginAt(newLastActiveAt);
  }
}
