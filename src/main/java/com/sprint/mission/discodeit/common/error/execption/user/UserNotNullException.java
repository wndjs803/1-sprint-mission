package com.sprint.mission.discodeit.common.error.execption.user;

import com.sprint.mission.discodeit.common.error.ErrorCode;
import com.sprint.mission.discodeit.common.error.execption.InvalidValueException;

public class UserNotNullException extends InvalidValueException {

  public UserNotNullException() {
    super(ErrorCode.USER_NOT_NULL.getMessage(), ErrorCode.USER_NOT_NULL);
  }
}
