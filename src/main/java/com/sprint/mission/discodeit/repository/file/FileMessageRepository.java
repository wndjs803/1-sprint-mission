package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Repository
public class FileMessageRepository implements MessageRepository {
    private final FileStorage fileStorage;
    private final Path directory = Paths.get(System.getProperty("user.dir"), "data", "message");

    public FileMessageRepository(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
        fileStorage.init(directory);
    }

    @Override
    public Message saveMessage(Message message) {
        Path filePath = directory.resolve(message.getId().toString().concat(".ser"));
        return fileStorage.save(filePath, message);
    }

    @Override
    public Message findMessageById(UUID messageId) {
        List<Message> messageList = fileStorage.load(directory);

        return messageList.stream()
                .filter(channel -> channel.getId().equals(messageId))
                .findFirst().get();
    }

    @Override
    public List<Message> findAllMessages() {
        return fileStorage.load(directory);
    }

    @Override
    public void removeMessage(UUID messageId) {
        Message message = findMessageById(messageId);
        fileStorage.remove(directory, message);
    }
}
