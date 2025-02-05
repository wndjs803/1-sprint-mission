package com.sprint.mission.discodeit.dto.channel.response;

import java.util.UUID;

public record CreatePublicChannelResponse(
        UUID ChannelId,
        String name,
        String description
) {
}
