package com.sprint.mission.discodeit.common.error.execption.readStatus;

import com.sprint.mission.discodeit.common.error.ErrorCode;
import com.sprint.mission.discodeit.common.error.execption.EntityNotFoundException;

public class ReadStatusNotFoundException extends EntityNotFoundException {

  public ReadStatusNotFoundException(String value) {
    super(ErrorCode.READSTATUS_NOT_FOUND.format(value), ErrorCode.READSTATUS_NOT_FOUND);
  }
}
