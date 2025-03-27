package com.sprint.mission.discodeit.execption.channel;

import com.sprint.mission.discodeit.execption.ErrorCode;
import com.sprint.mission.discodeit.execption.InvalidValueException;

public class NotChannelCreatorException extends InvalidValueException {

  public NotChannelCreatorException(String value) {
    super(ErrorCode.NOT_CHANNEL_CREATOR.format(value), ErrorCode.NOT_CHANNEL_CREATOR);
  }
}
