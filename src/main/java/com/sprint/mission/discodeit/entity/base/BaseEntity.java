package com.sprint.mission.discodeit.entity.base;

import com.sprint.mission.discodeit.global.util.TimeUtil;
import jakarta.persistence.EntityListeners;
import java.time.Instant;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

  private final UUID id;
  
  @CreatedDate
  private final Instant createdAt;

  public BaseEntity() {
    this.id = UUID.randomUUID();
    this.createdAt = TimeUtil.getCurrentTime();
  }

}
