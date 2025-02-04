package com.sprint.mission.discodeit.entity;

import lombok.Getter;

@Getter
public class ReadStatus extends BaseEntity {

    private final Message message;
    private boolean isRead = false;
    // 읽기 상태를 변경할 때마다 updatedAt이 변경됨으로 updatedAt을 통해 메세지를 마지막으로 읽은 시간을 알아낼 수 있다.

    public ReadStatus(Message message) {
        this.message = message;
    }

    public void updateRead(boolean read) {
        this.isRead = read;
    }
}
