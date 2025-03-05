package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import com.sprint.mission.discodeit.global.error.ErrorCode;
import java.time.Instant;
import lombok.Getter;

@Getter
public class ReadStatus extends BaseUpdatableEntity {

  private final User user;
  private final Channel channel;
  private Instant lastReadAt;

  private ReadStatus(User user, Channel channel, Instant lastReadAt) {
    super();
    this.user = user;
    this.channel = channel;
    this.lastReadAt = lastReadAt;
  }

  public static ReadStatus of(User user, Channel channel, Instant lastReadAt) {
    if (user == null) {
      throw new IllegalArgumentException(ErrorCode.USER_NOT_NULL.getMessage());
    }
    if (channel == null) {
      throw new IllegalArgumentException(ErrorCode.CHANNEL_NOT_NULL.getMessage());
    }
    return new ReadStatus(user, channel, lastReadAt);
  }

  public void updateLastReadAt(Instant lastReadAt) {
    this.lastReadAt = lastReadAt;
    updateUpdatedAt();
  }
}
