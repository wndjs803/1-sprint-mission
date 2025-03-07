package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.message.MessageDto;
import com.sprint.mission.discodeit.dto.message.request.CreateMessageRequest;
import com.sprint.mission.discodeit.dto.message.request.UpdateMessageRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.MessageAttachment;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.global.error.execption.message.MessageNotFoundException;
import com.sprint.mission.discodeit.global.util.MultipartFileConverter;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.jpa.MessageAttachmentJpaRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.validator.ChannelValidator;
import com.sprint.mission.discodeit.validator.UserValidator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {

  private final MessageRepository messageRepository;
  private final BinaryContentRepository binaryContentRepository;
  private final MessageAttachmentJpaRepository messageAttachmentRepository;

  private final UserValidator userValidator;
  private final ChannelValidator channelValidator;

  private final MultipartFileConverter multipartFileConverter;

  private final MessageMapper messageMapper;

  @Override
  @Transactional
  public MessageDto createMessage(CreateMessageRequest createMessageRequest,
      List<MultipartFile> multipartFileList) {
    User sender = userValidator.validateUserExistsByUserId(createMessageRequest.authorId());
    Channel foundChannel = channelValidator.validateChannelExistsByChannelId(
        createMessageRequest.channelId());
    Message message = messageRepository.saveMessage(
        messageMapper.toEntity(sender, foundChannel, createMessageRequest.content())
    );

    if (!multipartFileList.isEmpty()) {
      multipartFileList
          .forEach(multipartFile -> {
            BinaryContent binaryContent = BinaryContent.of(multipartFile.getOriginalFilename(),
                multipartFile.getContentType(), multipartFileConverter.toByteArray(multipartFile));
            BinaryContent savedContent = binaryContentRepository.saveBinaryContent(binaryContent);
            messageAttachmentRepository.save(
                MessageAttachment.of(message, savedContent)
            );
          });
    }

    return messageMapper.toMessageDto(message);
  }

  // 요구 사항에 없기에 시간 남으면 수정
  @Override
  public MessageDto findMessageByIdOrThrow(UUID messageId) {
    Message message = findMessageById(messageId);

    return messageMapper.toMessageDto(message);

  }

  @Override
  @Transactional(readOnly = true)
  public List<MessageDto> findAllMessagesByChannelId(UUID channelId) {
    Channel channel = channelValidator.validateChannelExistsByChannelId(channelId);
    return messageRepository.findAllMessagesByChannel(channel).stream()
        .map(message -> messageMapper.toMessageDto(message))
        .toList();
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
