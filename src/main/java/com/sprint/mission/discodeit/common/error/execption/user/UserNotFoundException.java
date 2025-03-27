package com.sprint.mission.discodeit.common.error.execption.user;

import com.sprint.mission.discodeit.common.error.ErrorCode;
import com.sprint.mission.discodeit.common.error.execption.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

  public UserNotFoundException(String value) {
    super(ErrorCode.USER_NOT_FOUND.format(value), ErrorCode.USER_NOT_FOUND);
  }
}
