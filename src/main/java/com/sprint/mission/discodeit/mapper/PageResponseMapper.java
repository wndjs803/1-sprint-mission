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
    return new PageResponse<>(page.getContent(),
        page.getContent().get(page.getContent().size() - 1),
        page.getSize(), page.hasNext(), page.getTotalElements());
  }
}
