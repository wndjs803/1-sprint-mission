package com.sprint.mission.discodeit.execption.readStatus;

import com.sprint.mission.discodeit.execption.ErrorCode;
import com.sprint.mission.discodeit.execption.EntityNotFoundException;

public class ReadStatusNotFoundException extends EntityNotFoundException {

  public ReadStatusNotFoundException(String value) {
    super(ErrorCode.READSTATUS_NOT_FOUND.format(value), ErrorCode.READSTATUS_NOT_FOUND);
  }
}
