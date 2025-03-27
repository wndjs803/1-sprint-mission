package com.sprint.mission.discodeit.execption.message;

import com.sprint.mission.discodeit.execption.ErrorCode;
import com.sprint.mission.discodeit.execption.InvalidValueException;

public class NotMessageCreatorException extends InvalidValueException {

  public NotMessageCreatorException(String value) {
    super(ErrorCode.NOT_MESSAGE_CREATOR.format(value), ErrorCode.NOT_MESSAGE_CREATOR);
  }
}
