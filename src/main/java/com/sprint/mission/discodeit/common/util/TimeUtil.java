package com.sprint.mission.discodeit.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeUtil {
    public static Instant getCurrentTime() {
        return Instant.now();
    }
}
