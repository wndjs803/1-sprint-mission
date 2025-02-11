package com.sprint.mission.discodeit.global.error.execption.user;

import com.sprint.mission.discodeit.global.error.execption.InvalidValueException;

public class UserNotNullException extends InvalidValueException {

    public UserNotNullException(String value) {
        super(value);
    }
}
