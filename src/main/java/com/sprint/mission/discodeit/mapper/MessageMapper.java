package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.binaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.dto.message.MessageDto;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageMapper {

  private final BinaryContentMapper binaryContentMapper;
  private final UserMapper userMapper;

  public Message toEntity(User sender, Channel channel, String content) {
    return Message.of(sender, channel, content);
  }

  public MessageDto toMessageDto(Message message) {
    UserDto userDto = userMapper.toUserDto(message.getSender());
    List<BinaryContentDto> attachments = message.getAttachmentsList().stream()
        .map(messageAttachment ->
            binaryContentMapper.toBinaryContentDto(messageAttachment.getAttachment()))
        .toList();

    return new MessageDto(message.getId(), message.getCreatedAt(), message.getUpdatedAt(),
        message.getContent(), message.getChannel().getId(), userDto, attachments);
  }
}
