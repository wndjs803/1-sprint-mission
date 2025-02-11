package com.sprint.mission.discodeit.validator;

import com.sprint.mission.discodeit.global.error.ErrorCode;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static com.sprint.mission.discodeit.global.error.ErrorCode.READSTATUS_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class ReadStatusValidator {

    private final ReadStatusRepository readStatusRepository;

    public ReadStatus validateReadStatusExistsByUserId(UUID userId) {
        return readStatusRepository.findReadStatusByUserId(userId)
                .orElseThrow(() -> new RuntimeException(READSTATUS_NOT_FOUND.format("userId: " + userId)));
    }

    public ReadStatus validateReadStatusExistsById(UUID readStatusId) {
        return Optional.ofNullable(readStatusRepository.findReadStatusById(readStatusId))
                .orElseThrow(() -> new RuntimeException(
                        ErrorCode.READSTATUS_NOT_FOUND.format("id" + readStatusId)));
    }
}
