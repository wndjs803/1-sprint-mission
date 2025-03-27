package com.sprint.mission.discodeit.common.error.execption;

import com.sprint.mission.discodeit.common.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

  public EntityNotFoundException(String value, ErrorCode errorCode) {
    super(value, errorCode);
  }
}
