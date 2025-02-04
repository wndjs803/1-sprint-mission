package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.common.UtilMethod;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

@Getter
public class BinaryContent implements Serializable {

    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final byte[] content;
    private final Instant createdAt;

    private BinaryContent(byte[] content) {
        this.id = UUID.randomUUID();
        this.content = content;
        this.createdAt = UtilMethod.getCurrentTime();
    }

    public static BinaryContent of(byte[] content) {
        return new BinaryContent(content);
    }

    @Override
    public String toString() {
        return "BinaryContent{" +
                "id=" + id +
                ", content=" + Arrays.toString(content) +
                ", createdAt=" + createdAt +
                '}';
    }
}
