package com.sprint.mission.discodeit.repository.jpa;

import com.sprint.mission.discodeit.entity.MessageAttachment;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageAttachmentJpaRepository extends JpaRepository<MessageAttachment, UUID> {

}
