package com.sprint.mission.discodeit.common.error.execption.bianryContent;

import com.sprint.mission.discodeit.common.error.ErrorCode;
import com.sprint.mission.discodeit.common.error.execption.InvalidValueException;

public class BinaryContentNotNullException extends InvalidValueException {

    public BinaryContentNotNullException() {
        super(ErrorCode.BINARYCONTENT_NOT_NULL.getMessage(), ErrorCode.BINARYCONTENT_NOT_NULL);
    }
}
