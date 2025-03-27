package com.sprint.mission.discodeit.execption;

import java.time.Instant;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

  private final Instant timestamp;
  private final String errorCode;
  private final String errorMessage;
  private final Map<String, Object> details;
  private final String exceptionType;
  private final int status;
}
