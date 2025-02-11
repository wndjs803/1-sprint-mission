package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.common.UtilMethod;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;
    private final ChannelService channelService;

    public BasicMessageService(MessageRepository messageRepository, UserService userService, ChannelService channelService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.channelService = channelService;
    }

    @Override
    public Message createMessage(UUID sendUserId, UUID channelId, String content) {
        User sendUser = userService.findUserByIdOrThrow(sendUserId);
        Channel foundChannel = channelService.findChannelByIdOrThrow(channelId);

        Message message = Message.of(sendUser, foundChannel, content);

        return messageRepository.saveMessage(message);
    }

    @Override
    public Message findMessageByIdOrThrow(UUID messageId) {
        return Optional.ofNullable(messageRepository.findMessageById(messageId))
                .orElseThrow(() -> new RuntimeException(ErrorMessage.MESSAGE_NOT_FOUND.format(messageId)));
    }

    @Override
    public List<Message> findAllMessage() {
        return messageRepository.findAllMessages();
    }

    @Override
    public Message updateMessage(UUID sendUserId, UUID messageId, String content) {
        userService.findUserByIdOrThrow(sendUserId);
        Message foundMessage = findMessageByIdOrThrow(messageId);

        if (foundMessage.isNotOwner(sendUserId)) {
            throw new RuntimeException(ErrorMessage.NOT_MESSAGE_CREATOR.format(sendUserId));
        }

        foundMessage.updateContent(content);
        foundMessage.updateUpdatedAt(UtilMethod.getCurrentTime());

        return messageRepository.saveMessage(foundMessage);
    }

    @Override
    public void deleteMessage(UUID sendUserId, UUID messageId) {
        Message foundMessage = findMessageByIdOrThrow(messageId);

        if (foundMessage.isNotOwner(sendUserId)) {
            throw new RuntimeException(ErrorMessage.NOT_MESSAGE_CREATOR.format(sendUserId));
        }

        messageRepository.removeMessage(messageId);
    }
}
