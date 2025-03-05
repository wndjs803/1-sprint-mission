package com.sprint.mission.discodeit.entity.base;

import com.sprint.mission.discodeit.global.util.TimeUtil;
import jakarta.persistence.EntityListeners;
import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseUpdatableEntity extends BaseEntity {

  @LastModifiedDate
  private Instant updatedAt;

  public BaseUpdatableEntity() {
    super();
    this.updatedAt = super.getCreatedAt();
  }

  public void updateUpdatedAt() {
    this.updatedAt = TimeUtil.getCurrentTime();
  }
}
