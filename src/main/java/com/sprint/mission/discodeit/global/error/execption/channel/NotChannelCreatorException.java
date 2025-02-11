package com.sprint.mission.discodeit.global.error.execption.channel;

import com.sprint.mission.discodeit.global.error.execption.InvalidValueException;

public class NotChannelCreatorException extends InvalidValueException {
    public NotChannelCreatorException(String value) {
        super(value);
    }
}
