package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageRepository {

  Message saveMessage(Message message);

  Optional<Message> findMessageById(UUID messageId);

  Page<Message> findAllMessagesByChannel(Channel channel, Pageable pageable);

  Page<Message> findAllMessagesByChannel(Channel channel, Instant cursor, Pageable pageable);

  Optional<Message> findLastMessage();

  void removeMessage(UUID messageId);
}
