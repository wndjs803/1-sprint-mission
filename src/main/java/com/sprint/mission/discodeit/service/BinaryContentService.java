package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.BinaryContent;

import java.util.List;
import java.util.UUID;

public interface BinaryContentService {
    BinaryContent createBinaryContent(byte[] content);

    BinaryContent findBinaryContentById(UUID binaryContentId);

    BinaryContent findAllBinaryContentsById(List<UUID> binaryContentIdList);

    void deleteBinaryContent(UUID binaryContentId);
}
