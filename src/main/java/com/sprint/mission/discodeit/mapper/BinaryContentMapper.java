package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.binaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.MessageAttachment;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BinaryContentMapper {

  BinaryContentDto toBinaryContentDto(BinaryContent binaryContent);

  List<BinaryContentDto> toBinaryContentDtoList(List<MessageAttachment> attachments);

  default BinaryContentDto toBinaryContentDto(MessageAttachment messageAttachment) {
    BinaryContent binaryContent = messageAttachment.getAttachment();
    return this.toBinaryContentDto(binaryContent);
  }
}
