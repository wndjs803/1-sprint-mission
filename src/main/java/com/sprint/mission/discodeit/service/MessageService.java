package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.message.MessageDto;
import com.sprint.mission.discodeit.dto.message.request.CreateMessageRequest;
import com.sprint.mission.discodeit.dto.message.request.UpdateMessageRequest;
import com.sprint.mission.discodeit.dto.response.PageResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface MessageService {

  public MessageDto createMessage(CreateMessageRequest createMessageRequest,
      List<MultipartFile> multipartFileList);

  MessageDto findMessageByIdOrThrow(UUID messageId);

  PageResponse<MessageDto> findAllMessagesByChannelId(UUID channelId, Pageable pageable);

  MessageDto updateMessage(UUID messageId, UpdateMessageRequest updateMessageRequest);

  void deleteMessage(UUID messageId);
}
