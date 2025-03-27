package com.sprint.mission.discodeit.execption.user;

import com.sprint.mission.discodeit.execption.ErrorCode;
import com.sprint.mission.discodeit.execption.InvalidValueException;

public class UserNotNullException extends InvalidValueException {

  public UserNotNullException() {
    super(ErrorCode.USER_NOT_NULL.getMessage(), ErrorCode.USER_NOT_NULL);
  }
}
