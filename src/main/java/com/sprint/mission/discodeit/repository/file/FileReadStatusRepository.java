package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@Repository
//@FileRepositoryCondition
public class FileReadStatusRepository implements ReadStatusRepository {

  private final FileStorage fileStorage;
  private final Path directory;
  private static final String SUBDIRECTORY = "readStatus";

  public FileReadStatusRepository(FileStorage fileStorage) {
    this.fileStorage = fileStorage;
    directory = fileStorage.getDirectory(SUBDIRECTORY);
    fileStorage.init(directory);
  }

  @Override
  public ReadStatus saveReadStatus(ReadStatus readStatus) {
    Path filePath = fileStorage.getFilePath(directory, readStatus.getId());
    return fileStorage.save(filePath, readStatus);
  }

  @Override
  public Optional<ReadStatus> findReadStatusById(UUID readStatusId) {
    List<ReadStatus> readStatusList = fileStorage.load(directory);

    return readStatusList.stream()
        .filter(readStatus -> readStatus.getId().equals(readStatusId))
        .findFirst();
  }

  public Optional<ReadStatus> findReadStatusByUserId(UUID userId) {
    List<ReadStatus> readStatusList = fileStorage.load(directory);

    return readStatusList.stream()
        .filter(readStatus -> readStatus.getUser().getId().equals(userId))
        .findFirst();
  }

  @Override
  public List<ReadStatus> findAllReadStatusByUser(User user) {
    return null;
  }

  @Override
  public List<ReadStatus> findAllReadStatusByChannel(Channel channel) {
    return null;
  }

  @Override
  public void removeReadStatus(UUID readStatusId) {
    ReadStatus readStatus = findReadStatusById(readStatusId).get();
    fileStorage.remove(directory, readStatus);
  }
}
