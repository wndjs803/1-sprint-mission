package com.sprint.mission.discodeit.global.error.execption.channel;

import com.sprint.mission.discodeit.global.error.execption.InvalidValueException;

public class CannotUpdatePrivateChannelException extends InvalidValueException {

    public CannotUpdatePrivateChannelException(String value) {
        super(value);
    }
}
