package com.sprint.mission.discodeit.global.error.execption.channel;

import com.sprint.mission.discodeit.global.error.execption.InvalidValueException;

public class ChannelNotNullException extends InvalidValueException {

    public ChannelNotNullException(String value) {
        super(value);
    }
}
