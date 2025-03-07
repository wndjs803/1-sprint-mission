package com.sprint.mission.discodeit.repository.jpa.message;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageJpaRepository extends JpaRepository<Message, UUID> {

  Page<Message> findAllByChannel(Channel channel, Pageable pageable);

  Optional<Message> findFirstByOrderByCreatedAtDesc();

}
