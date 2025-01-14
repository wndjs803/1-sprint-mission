package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.MessageService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class JCFMessageService implements MessageService {
    private final Map<UUID, Message> messageData;
    private final JCFUserService jcfUserService;
    private final JCFChannelService jcfChannelService;

    public JCFMessageService(JCFUserService jcfUserService, JCFChannelService jcfChannelService) {
        messageData = new HashMap<>();
        this.jcfUserService = jcfUserService;
        this.jcfChannelService = jcfChannelService;
    }

    @Override
    public Message createMessage(UUID sendUserId, UUID channelId, String content) {
        User sendUser = jcfUserService.findUserByIdOrThrow(sendUserId);
        Channel foundChannel = jcfChannelService.findChannelByIdOrThrow(channelId);

        Message message = Message.of(sendUser, foundChannel, content);

        messageData.put(message.getId(), message);

        return message;
    }

    @Override
    public Message findMessageByIdOrThrow(UUID messageId) {
        return Optional.ofNullable(messageData.get(messageId))
                .orElseThrow(() -> new RuntimeException(ErrorMessage.MESSAGE_NOT_FOUND.getMessage()));
    }

    @Override
    public List<Message> findAllMessage() {
        return new ArrayList<>(messageData.values());
    }

    @Override
    public void updateMessage(UUID sendUserId, UUID messageId, String content) {
        jcfUserService.findUserByIdOrThrow(sendUserId);
        Message foundMessage = findMessageByIdOrThrow(messageId);

        if(foundMessage.isNotOwner(sendUserId)){
            throw new RuntimeException(ErrorMessage.NOT_MESSAGE_CREATOR.getMessage());
        }

        foundMessage.updateContent(content);
        foundMessage.updateUpdatedAt(Instant.now().toEpochMilli());

        messageData.put(foundMessage.getId(), foundMessage);
    }

    @Override
    public void deleteMessage(UUID sendUserId, UUID messageId) {
        // 메세지 조회
        Message foundMessage = findMessageByIdOrThrow(messageId);

        // 메세지 생성자가 맞는지 확인
        if(foundMessage.isNotOwner(sendUserId)){
            throw new RuntimeException(ErrorMessage.NOT_MESSAGE_CREATOR.getMessage());
        }

        // 메세지 삭제
        messageData.remove(messageId);
    }
}
