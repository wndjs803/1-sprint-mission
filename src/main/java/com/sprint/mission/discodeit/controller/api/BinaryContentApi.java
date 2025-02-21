package com.sprint.mission.discodeit.controller.api;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.global.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

@Tag(name = "BinaryContent", description = "첨부 파일 API")
public interface BinaryContentApi {

  @Operation(summary = "여러 첨부 파일 조회")
  ResponseEntity<ResultResponse<List<BinaryContent>>> findBinaryContents(
      @Parameter(description = "조회할 첨부 파일 ID 목록") List<UUID> binaryContentIdList
  );
}
