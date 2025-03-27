package com.sprint.mission.discodeit.execption.userStatus;

import com.sprint.mission.discodeit.execption.ErrorCode;
import com.sprint.mission.discodeit.execption.EntityNotFoundException;

public class UserStatusNotFoundException extends EntityNotFoundException {

  public UserStatusNotFoundException(String value) {
    super(ErrorCode.USERSTATUS_NOT_FOUND.format(value), ErrorCode.USERSTATUS_NOT_FOUND);
  }
}
