package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.binaryContent.BinaryContentDto;
import java.io.InputStream;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

public interface BinaryContentStorage {

  UUID put(UUID id, byte[] content);

  InputStream get(UUID id);

  ResponseEntity<?> download(BinaryContentDto binaryContentDto);
}
