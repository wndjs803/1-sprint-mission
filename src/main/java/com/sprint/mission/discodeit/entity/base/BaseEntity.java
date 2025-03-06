package com.sprint.mission.discodeit.entity.base;

import com.sprint.mission.discodeit.global.util.TimeUtil;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Getter
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private final UUID id;

  @CreatedDate
  @Column(name = "created_at", nullable = false)
  private final Instant createdAt;

  public BaseEntity() {
    this.id = UUID.randomUUID();
    this.createdAt = TimeUtil.getCurrentTime();
  }

}
