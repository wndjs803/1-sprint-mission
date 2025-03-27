package com.sprint.mission.discodeit.execption.channel;

import com.sprint.mission.discodeit.execption.ErrorCode;
import com.sprint.mission.discodeit.execption.InvalidValueException;

public class ChannelNotNullException extends InvalidValueException {

  public ChannelNotNullException() {
    super(ErrorCode.CHANNEL_NOT_NULL.getMessage(), ErrorCode.CHANNEL_NOT_NULL);
  }
}
