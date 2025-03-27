package com.sprint.mission.discodeit.common.error.execption.channel;

import com.sprint.mission.discodeit.common.error.ErrorCode;
import com.sprint.mission.discodeit.common.error.execption.EntityNotFoundException;

public class ChannelNotFoundException extends EntityNotFoundException {

    public ChannelNotFoundException(String value) {
        super(ErrorCode.CHANNEL_NOT_FOUND.format(value), ErrorCode.CHANNEL_NOT_FOUND);
    }
}
