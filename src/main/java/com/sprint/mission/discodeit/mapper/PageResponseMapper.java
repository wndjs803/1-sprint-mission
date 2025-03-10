package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageResponseMapper<T> {

  public PageResponse<T> fromSlice(Slice<T> slice) {
    return null;
  }

  public PageResponse<T> fromPage(Page<T> page) {
    Object nextCursor = null;
    if (page.hasContent()) {
      nextCursor = page.getContent().get(page.getContent().size() - 1);
    }
    return new PageResponse<>(page.getContent(), nextCursor, page.getSize(), page.hasNext(),
        page.getTotalElements());
  }
}
