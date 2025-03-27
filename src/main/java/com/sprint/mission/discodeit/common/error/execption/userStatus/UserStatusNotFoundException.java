package com.sprint.mission.discodeit.common.error.execption.userStatus;

import com.sprint.mission.discodeit.common.error.ErrorCode;
import com.sprint.mission.discodeit.common.error.execption.EntityNotFoundException;

public class UserStatusNotFoundException extends EntityNotFoundException {

  public UserStatusNotFoundException(String value) {
    super(ErrorCode.USERSTATUS_NOT_FOUND.format(value), ErrorCode.USERSTATUS_NOT_FOUND);
  }
}
