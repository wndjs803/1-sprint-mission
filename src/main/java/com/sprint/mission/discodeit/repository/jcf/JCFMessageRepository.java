package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JCFMessageRepository implements MessageRepository {
    private final Map<UUID, Message> messageData = new HashMap<>();

    private JCFMessageRepository() {
    }

    public static JCFMessageRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final JCFMessageRepository INSTANCE = new JCFMessageRepository();
    }

    @Override
    public Message saveMessage(Message message) {
        messageData.put(message.getId(), message);
        return messageData.get(message.getId());
    }

    @Override
    public Message findMessageById(UUID messageId) {
        return messageData.get(messageId);
    }

    @Override
    public List<Message> findAllMessages() {
        return new ArrayList<>(messageData.values());
    }

    @Override
    public void removeMessage(UUID messageId) {
        messageData.remove(messageId);
    }
}
