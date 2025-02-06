package com.sprint.mission.discodeit.dto.readStatus.request;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;

public record CreateReadStatusRequest(
        User user,
        Channel channel
) {
}
