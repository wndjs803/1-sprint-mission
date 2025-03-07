package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.message.MessageDto;
import com.sprint.mission.discodeit.dto.response.PageResponse;
import com.sprint.mission.discodeit.entity.Message;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageResponseMapper<T> {

  private final MessageMapper messageMapper;

  public PageResponse<T> fromsSlice(Slice<T> slice) {
    return null;
  }

  public PageResponse<T> fromsPage(Page<T> page) {
    List<MessageDto> messageDtoList = page.getContent().stream()
        .map(message -> messageMapper.toMessageDto((Message) message))
        .toList();
    return new PageResponse<T>((List<T>) messageDtoList, page.getNumber(), page.getSize(),
        page.hasNext(), page.getTotalElements());
  }

}
