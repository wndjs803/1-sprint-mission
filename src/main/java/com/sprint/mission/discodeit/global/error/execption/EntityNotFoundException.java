package com.sprint.mission.discodeit.global.error.execption;

import com.sprint.mission.discodeit.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException{

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
