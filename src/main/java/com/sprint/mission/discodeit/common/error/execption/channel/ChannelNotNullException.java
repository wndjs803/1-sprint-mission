package com.sprint.mission.discodeit.common.error.execption.channel;

import com.sprint.mission.discodeit.common.error.ErrorCode;
import com.sprint.mission.discodeit.common.error.execption.InvalidValueException;

public class ChannelNotNullException extends InvalidValueException {

  public ChannelNotNullException() {
    super(ErrorCode.CHANNEL_NOT_NULL.getMessage(), ErrorCode.CHANNEL_NOT_NULL);
  }
}
