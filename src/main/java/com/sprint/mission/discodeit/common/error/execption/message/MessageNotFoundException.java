package com.sprint.mission.discodeit.common.error.execption.message;

import com.sprint.mission.discodeit.common.error.ErrorCode;
import com.sprint.mission.discodeit.common.error.execption.EntityNotFoundException;

public class MessageNotFoundException extends EntityNotFoundException {

    public MessageNotFoundException(String value) {
        super(ErrorCode.MESSAGE_NOT_FOUND.format(value), ErrorCode.MESSAGE_NOT_FOUND);
    }
}
