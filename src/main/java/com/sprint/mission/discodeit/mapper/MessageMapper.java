package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.message.MessageDto;
import com.sprint.mission.discodeit.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BinaryContentMapper.class, UserMapper.class})
public interface MessageMapper {

//  public MessageDto toMessageDto(Message message) {
//    UserDto userDto = userMapper.toUserDto(message.getSender());
//    List<BinaryContentDto> attachments = message.getAttachmentsList().stream()
//        .map(messageAttachment ->
//            binaryContentMapper.toBinaryContentDto(
//                messageAttachment.getAttachment())) // n + 1 문제 발생
//        .toList();
//
//    return new MessageDto(message.getId(), message.getCreatedAt(), message.getUpdatedAt(),
//        message.getContent(), message.getChannel().getId(), userDto, attachments);
//  }

  @Mapping(target = "author", source = "sender")
  @Mapping(target = "channelId", expression = "java(message.getChannel().getId())")
  @Mapping(target = "attachments", source = "attachmentsList")
  MessageDto toMessageDto(Message message);
}
