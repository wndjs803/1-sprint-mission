package com.sprint.mission.discodeit.execption;

public class EntityNotFoundException extends BusinessException {

  public EntityNotFoundException(String value, ErrorCode errorCode) {
    super(value, errorCode);
  }
}
