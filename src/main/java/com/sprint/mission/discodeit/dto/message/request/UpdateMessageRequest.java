package com.sprint.mission.discodeit.dto.message.request;

import java.util.UUID;

public record UpdateMessageRequest(
        UUID sendUserId,
        UUID messageId,
        String content
) {
}
