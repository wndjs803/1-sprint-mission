package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.common.MultipartFileConverter;
import com.sprint.mission.discodeit.common.TimeUtil;
import com.sprint.mission.discodeit.dto.message.request.CreateMessageRequest;
import com.sprint.mission.discodeit.dto.message.response.CreateMessageResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.validator.ChannelValidator;
import com.sprint.mission.discodeit.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {

//    @Qualifier("fileMessageRepository")
    @Qualifier("jcfMessageRepository")
    private final MessageRepository messageRepository;
    private final UserValidator userValidator;
    private final ChannelValidator channelValidator;
    private final MultipartFileConverter multipartFileConverter;
    private final MessageMapper messageMapper;

    @Override
    public CreateMessageResponse createMessage(CreateMessageRequest createMessageRequest,
                                               List<MultipartFile> multipartFileList) {

        User sendUser = userValidator.validateUserExistsByUserId(createMessageRequest.sendUserId());
        Channel foundChannel = channelValidator.validateChannelExistsByChannelId(createMessageRequest.channelId());

        Message message = messageMapper.toEntity(sendUser, foundChannel, createMessageRequest.content());

        multipartFileList.forEach(
                multipartFile -> {
                    BinaryContent binaryContent = BinaryContent.of(multipartFileConverter.toByteArray(multipartFile));
                    message.addBinaryContent(binaryContent);
                }
        );

        return messageMapper.toCreateMessageResponse(messageRepository.saveMessage(message));
    }

    @Override
    public Message findMessageByIdOrThrow(UUID messageId) {
        return Optional.ofNullable(messageRepository.findMessageById(messageId))
                .orElseThrow(() -> new RuntimeException(ErrorMessage.MESSAGE_NOT_FOUND.format(messageId)));
    }

    @Override
    public List<Message> findAllMessage() {
        return messageRepository.findAllMessages();
    }

    @Override
    public Message updateMessage(UUID sendUserId, UUID messageId, String content) {
        userValidator.validateUserExistsByUserId(sendUserId);
        Message foundMessage = findMessageByIdOrThrow(messageId);

        if (foundMessage.isNotOwner(sendUserId)) {
            throw new RuntimeException(ErrorMessage.NOT_MESSAGE_CREATOR.format(sendUserId));
        }

        foundMessage.updateContent(content);
        foundMessage.updateUpdatedAt(TimeUtil.getCurrentTime());

        return messageRepository.saveMessage(foundMessage);
    }

    @Override
    public void deleteMessage(UUID sendUserId, UUID messageId) {
        Message foundMessage = findMessageByIdOrThrow(messageId);

        if (foundMessage.isNotOwner(sendUserId)) {
            throw new RuntimeException(ErrorMessage.NOT_MESSAGE_CREATOR.format(sendUserId));
        }

        messageRepository.removeMessage(messageId);
    }
}
