package com.sprint.mission.discodeit.common.error.execption.bianryContent;

import com.sprint.mission.discodeit.common.error.ErrorCode;
import com.sprint.mission.discodeit.common.error.execption.EntityNotFoundException;

public class BinaryContentNofFoundException extends EntityNotFoundException {
    public BinaryContentNofFoundException(String value) {
        super(ErrorCode.BINARYCONTENT_NOT_FOUND.format(value), ErrorCode.BINARYCONTENT_NOT_FOUND);
    }
}
