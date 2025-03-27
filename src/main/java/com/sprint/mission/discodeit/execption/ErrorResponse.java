package com.sprint.mission.discodeit.execption;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

  private String errorCode;
  private String errorMessage;
}
