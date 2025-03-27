package com.sprint.mission.discodeit.common.error.execption;

import com.sprint.mission.discodeit.common.error.ErrorCode;

public class InvalidValueException extends BusinessException {

    public InvalidValueException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}
