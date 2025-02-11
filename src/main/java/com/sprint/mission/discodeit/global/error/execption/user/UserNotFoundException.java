package com.sprint.mission.discodeit.global.error.execption.user;

import com.sprint.mission.discodeit.global.error.execption.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
