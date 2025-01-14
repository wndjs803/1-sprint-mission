package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.FileStorage;
import com.sprint.mission.discodeit.service.MessageService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileMessageService implements MessageService {
    private final FileStorage fileStorage;
    private final FileUserService fileUserService;
    private final FileChannelService fileChannelService;

    private final Path directory = Paths.get(System.getProperty("user.dir"), "data", "message");

    public FileMessageService(FileStorage fileStorage, FileUserService fileUserService, FileChannelService fileChannelService) {
        this.fileStorage = fileStorage;
        this.fileUserService = fileUserService;
        this.fileChannelService = fileChannelService;
        fileStorage.init(directory);
    }

    @Override
    public Message createMessage(UUID sendUserId, UUID channelId, String content) {
        User sendUser = fileUserService.findUserByIdOrThrow(sendUserId);
        Channel foundChannel = fileChannelService.findChannelByIdOrThrow(channelId);

        Message message = Message.of(sendUser, foundChannel, content);

        Path filePath = directory.resolve(message.getId().toString().concat(".ser"));
        fileStorage.save(filePath, message);

        return message;
    }

    @Override
    public Message findMessageByIdOrThrow(UUID messageId) {
        List<Message> messageList = fileStorage.load(directory);

        Optional<Message> optionalMessage = messageList.stream()
                .filter(channel -> channel.getId().equals(messageId))
                .findFirst();

        return optionalMessage.orElseThrow(() -> new RuntimeException(ErrorMessage.MESSAGE_NOT_FOUND.getMessage()));
    }

    @Override
    public List<Message> findAllMessage() {
        return fileStorage.load(directory);
    }

    @Override
    public Message updateMessage(UUID sendUserId, UUID messageId, String content) {
        fileUserService.findUserByIdOrThrow(sendUserId);
        Message foundMessage = findMessageByIdOrThrow(messageId);

        if(foundMessage.isNotOwner(sendUserId)){
            throw new RuntimeException(ErrorMessage.NOT_MESSAGE_CREATOR.getMessage());
        }

        foundMessage.updateContent(content);
        foundMessage.updateUpdatedAt(Instant.now().toEpochMilli());

        Path filePath = directory.resolve(foundMessage.getId().toString().concat(".ser"));
        fileStorage.save(filePath, foundMessage);

        return foundMessage; // 임시 방편
    }

    @Override
    public void deleteMessage(UUID sendUserId, UUID messageId) {
        Message foundMessage = findMessageByIdOrThrow(messageId);

        if(foundMessage.isNotOwner(sendUserId)){
            throw new RuntimeException(ErrorMessage.NOT_MESSAGE_CREATOR.getMessage());
        }

        fileStorage.remove(directory, foundMessage);
    }
}
