package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.common.UtilMethod;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class BinaryContent implements Serializable {

    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final byte[] content;
    private final Instant createdAt;

    public BinaryContent(byte[] content) {
        this.id = UUID.randomUUID();
        this.content = content;
        this.createdAt = UtilMethod.getCurrentTime();
    }
}
