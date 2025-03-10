package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.message.MessageDto;
import com.sprint.mission.discodeit.dto.message.request.CreateMessageRequest;
import com.sprint.mission.discodeit.dto.message.request.UpdateMessageRequest;
import com.sprint.mission.discodeit.dto.response.PageResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.global.error.execption.message.MessageNotFoundException;
import com.sprint.mission.discodeit.global.util.MultipartFileConverter;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.mapper.PageResponseMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import com.sprint.mission.discodeit.validator.ChannelValidator;
import com.sprint.mission.discodeit.validator.UserValidator;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {

  private final MessageRepository messageRepository;
  private final BinaryContentRepository binaryContentRepository;

  private final UserValidator userValidator;
  private final ChannelValidator channelValidator;

  private final MessageMapper messageMapper;
  private final PageResponseMapper<MessageDto> pageResponseMapper;

  private final MultipartFileConverter multipartFileConverter;
  private final BinaryContentStorage binaryContentStorage;

  @Override
  @Transactional
  public MessageDto createMessage(CreateMessageRequest createMessageRequest,
      List<MultipartFile> multipartFileList) {
    User sender = userValidator.validateUserExistsByUserId(createMessageRequest.authorId());
    Channel foundChannel = channelValidator.validateChannelExistsByChannelId(
        createMessageRequest.channelId());
    Message message = messageRepository.saveMessage(
        Message.of(sender, foundChannel, createMessageRequest.content())
    );

    if (multipartFileList != null) {
      multipartFileList
          .forEach(multipartFile -> {
            BinaryContent binaryContent = BinaryContent.of(multipartFile.getOriginalFilename(),
                multipartFile.getSize(), multipartFile.getContentType());
            BinaryContent savedContent = binaryContentRepository.saveBinaryContent(binaryContent);

            binaryContentStorage.put(savedContent.getId(),
                multipartFileConverter.toByteArray(multipartFile));

            message.addAttachment(savedContent);
          });
    }

    return messageMapper.toMessageDto(message);
  }

  @Override
  @Transactional(readOnly = true)
  public PageResponse<MessageDto> findAllMessagesByChannelId(UUID channelId, Instant cursor,
      Pageable pageable) {
    Channel channel = channelValidator.validateChannelExistsByChannelId(channelId);
    Page<Message> messagePage =
        cursor == null ? messageRepository.findAllMessagesByChannel(channel, pageable)
            : messageRepository.findAllMessagesByChannel(channel, cursor, pageable);
    List<MessageDto> messageDtoList = messagePage.getContent().stream()
        .map(message -> messageMapper.toMessageDto(message))
        .toList();

    Page<MessageDto> messageDtoPage = new PageImpl<MessageDto>(messageDtoList, pageable,
        messagePage.getTotalElements());

    return pageResponseMapper.fromPage(messageDtoPage);
  }

  @Override
  @Transactional
  public MessageDto updateMessage(UUID messageId, UpdateMessageRequest updateMessageRequest) {
    Message foundMessage = findMessageById(messageId);

    foundMessage.updateContent(updateMessageRequest.newContent());

    return messageMapper.toMessageDto(foundMessage);
  }

  @Override
  @Transactional
  public void deleteMessage(UUID messageId) {
    findMessageById(messageId);

    messageRepository.removeMessage(messageId);
  }

  private Message findMessageById(UUID messageId) {
    return messageRepository.findMessageById(messageId)
        .orElseThrow(() -> new MessageNotFoundException("id: " + messageId));
  }
}
