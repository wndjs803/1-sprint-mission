package com.sprint.mission.discodeit.global.error.execption.channel;

import com.sprint.mission.discodeit.global.error.execption.EntityNotFoundException;

public class ChannelNotFoundException extends EntityNotFoundException {

    public ChannelNotFoundException(String message) {
        super(message);
    }
}
