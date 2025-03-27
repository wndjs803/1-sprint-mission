package com.sprint.mission.discodeit.execption.bianryContent;

import com.sprint.mission.discodeit.execption.ErrorCode;
import com.sprint.mission.discodeit.execption.InvalidValueException;

public class BinaryContentNotNullException extends InvalidValueException {

  public BinaryContentNotNullException() {
    super(ErrorCode.BINARYCONTENT_NOT_NULL.getMessage(), ErrorCode.BINARYCONTENT_NOT_NULL);
  }
}
