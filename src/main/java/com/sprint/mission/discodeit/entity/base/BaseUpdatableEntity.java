package com.sprint.mission.discodeit.entity.base;

import com.sprint.mission.discodeit.global.util.TimeUtil;
import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public abstract class BaseUpdatableEntity extends BaseEntity {

  private Instant updatedAt;

  public BaseUpdatableEntity() {
    super();
    this.updatedAt = super.getCreatedAt();
  }

  public void updateUpdatedAt() {
    this.updatedAt = TimeUtil.getCurrentTime();
  }
}
