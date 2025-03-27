package com.sprint.mission.discodeit.common.error.execption.user;

import com.sprint.mission.discodeit.common.error.ErrorCode;
import com.sprint.mission.discodeit.common.error.execption.InvalidValueException;

public class UserAlreadyExistException extends InvalidValueException {

  public UserAlreadyExistException(String value) {
    super(ErrorCode.USER_ALREADY_EXIST.format(value), ErrorCode.USER_ALREADY_EXIST);
  }
}
