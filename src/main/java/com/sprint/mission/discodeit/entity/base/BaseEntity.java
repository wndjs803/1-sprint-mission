package com.sprint.mission.discodeit.entity.base;

import com.sprint.mission.discodeit.global.util.TimeUtil;
import java.time.Instant;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public abstract class BaseEntity {

  private final UUID id;
  private final Instant createdAt;

  public BaseEntity() {
    this.id = UUID.randomUUID();
    this.createdAt = TimeUtil.getCurrentTime();
  }

}
