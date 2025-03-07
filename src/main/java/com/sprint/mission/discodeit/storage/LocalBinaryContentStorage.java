package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.binaryContent.BinaryContentDto;
import java.io.InputStream;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LocalBinaryContentStorage implements BinaryContentStorage {

  @Override
  public UUID put(UUID id, byte[] content) {
    return null;
  }

  @Override
  public InputStream get(UUID id) {
    return null;
  }

  @Override
  public ResponseEntity<?> download(BinaryContentDto binaryContentDto) {
    return null;
  }
}
