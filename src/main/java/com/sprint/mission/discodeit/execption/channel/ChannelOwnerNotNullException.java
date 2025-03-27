package com.sprint.mission.discodeit.execption.channel;

import com.sprint.mission.discodeit.execption.ErrorCode;
import com.sprint.mission.discodeit.execption.InvalidValueException;

public class ChannelOwnerNotNullException extends InvalidValueException {

  public ChannelOwnerNotNullException() {
    super(ErrorCode.CHANNEL_OWNER_NOT_NULL.getMessage(), ErrorCode.CHANNEL_OWNER_NOT_NULL);
  }
}
