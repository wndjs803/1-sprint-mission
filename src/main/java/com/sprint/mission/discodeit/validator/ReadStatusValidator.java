package com.sprint.mission.discodeit.validator;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.sprint.mission.discodeit.common.ErrorMessage.READSTATUS_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class ReadStatusValidator {

    private final ReadStatusRepository readStatusRepository;

    public ReadStatus validateReadStatusExistsByUserId(UUID userId) {
        return readStatusRepository.findReadStatusByUserId(userId)
                .orElseThrow(() -> new RuntimeException(READSTATUS_NOT_FOUND.format("userId: " + userId)));
    }
}
