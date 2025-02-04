package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ReadStatus;

import java.util.UUID;

public interface ReadStatusRepository {
    ReadStatus saveReadStatus(ReadStatus readStatus);
    ReadStatus findReadStatusByReadStatusId(UUID readStatusId);
    ReadStatus findReadStatusByUserId(UUID userId);
    void removeReadStatus(UUID readStatusId);
}
