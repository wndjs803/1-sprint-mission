package com.sprint.mission.discodeit.common.error.execption.channel;

import com.sprint.mission.discodeit.common.error.ErrorCode;
import com.sprint.mission.discodeit.common.error.execption.InvalidValueException;

public class ChannelOwnerNotNullException extends InvalidValueException {

    public ChannelOwnerNotNullException() {
        super(ErrorCode.CHANNEL_OWNER_NOT_NULL.getMessage(), ErrorCode.CHANNEL_OWNER_NOT_NULL);
    }
}
