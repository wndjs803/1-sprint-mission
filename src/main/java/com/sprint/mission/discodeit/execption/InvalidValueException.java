package com.sprint.mission.discodeit.execption;

public class InvalidValueException extends BusinessException {

  public InvalidValueException(String value, ErrorCode errorCode) {
    super(value, errorCode);
  }
}
