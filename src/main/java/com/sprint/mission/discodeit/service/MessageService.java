package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.message.request.CreateMessageRequest;
import com.sprint.mission.discodeit.dto.message.response.CreateMessageResponse;
import com.sprint.mission.discodeit.entity.Message;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    public CreateMessageResponse createMessage(CreateMessageRequest createMessageRequest,
                                               List<MultipartFile> multipartFileList);

    Message findMessageByIdOrThrow(UUID messageId);

    List<Message> findAllMessages();

    Message updateMessage(UUID sendUserId, UUID messageId, String content);

    void deleteMessage(UUID sendUserId, UUID messageId);
}
