package com.sprint.mission.discodeit.global.error.execption.channel;

import com.sprint.mission.discodeit.global.error.execption.InvalidValueException;

public class ChannelOwnerNotNullException extends InvalidValueException {

    public ChannelOwnerNotNullException(String value) {
        super(value);
    }
}
