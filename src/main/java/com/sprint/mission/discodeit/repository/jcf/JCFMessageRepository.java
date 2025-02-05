package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository("jcfMessageRepository")
@NoArgsConstructor
public class JCFMessageRepository implements MessageRepository {
    private final Map<UUID, Message> messageData = new HashMap<>();

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
    public List<Message> findAllMessagesByChannel(Channel channel) {
        return messageData.values().stream()
                .filter(message -> message.getChannel() == channel)
                .toList();
    }

    @Override
    public void removeMessage(UUID messageId) {
        messageData.remove(messageId);
    }
}
