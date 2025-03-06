package com.sprint.mission.discodeit.repository.jpa.message;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageJpaRepository extends JpaRepository<Message, UUID> {

  List<Message> findAllByChannel(Channel channel);

  Optional<Message> findFirstByOrderByCreatedAtDesc();

}
