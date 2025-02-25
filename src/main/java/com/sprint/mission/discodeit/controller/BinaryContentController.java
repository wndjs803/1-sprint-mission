package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.controller.api.BinaryContentApi;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.service.BinaryContentService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/binary-content")
public class BinaryContentController implements BinaryContentApi {

  private final BinaryContentService binaryContentService;

  @Override
  public ResponseEntity<BinaryContent> find(UUID binaryContentId) {
    return null;
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<List<BinaryContent>> findBinaryContents(
      @RequestBody List<UUID> binaryContentIdList) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(binaryContentService.findAllBinaryContentsById(binaryContentIdList));
  }
}
