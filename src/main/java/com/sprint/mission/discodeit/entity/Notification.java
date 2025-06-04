package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Notification extends BaseEntity {

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @ManyToOne(fetch = FetchType.LAZY) // on delete set null을 기능적으로 지원하지 않는 듯 하다.
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private UUID targetId;
}
