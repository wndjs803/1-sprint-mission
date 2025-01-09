package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.ErrorMessage;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.MessageService;

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
        User findUser = jcfUserService.findUserById(sendUserId);
        Channel findChannel = jcfChannelService.findChannelById(channelId);

        Message newMessage = new Message(findUser, findChannel, content);

        messageData.put(newMessage.getId(), newMessage);

        return newMessage;
    }

    @Override
    public Message findMessageById(UUID messageId) {
        return Optional.ofNullable(messageData.get(messageId))
                .orElseThrow(() -> new RuntimeException(ErrorMessage.MESSAGE_NOT_FOUND));
    }

    @Override
    public List<Message> findAllMessage() {
        return new ArrayList<>(messageData.values());
    }

    @Override
    public void updateMessage(UUID sendUserId, UUID messageId, String content) {

    }

    @Override
    public void deleteMessage(UUID sendUserId, UUID messageId) {

    }
}
