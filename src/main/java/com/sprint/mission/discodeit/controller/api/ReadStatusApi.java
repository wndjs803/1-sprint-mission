package com.sprint.mission.discodeit.controller.api;

import com.sprint.mission.discodeit.dto.readStatus.request.CreateReadStatusRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.global.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

@Tag(name = "ReadStatus", description = "Message 읽음 상태 API")
public interface ReadStatusApi {

  @Operation(summary = "Message 읽음 상태 생성")
  ResponseEntity<ResultResponse<ReadStatus>> createReadStatus(
      @Parameter(description = "Message 읽음 상태 생성 정보") CreateReadStatusRequest request
  );

  @Operation(summary = "Message 읽음 상태 수정")
  ResponseEntity<ResultResponse<ReadStatus>> updateReadStatus(
      @Parameter(description = "수정할 읽음 상태 ID") UUID readStatusId
  );

  @Operation(summary = "Message 읽음 상태 조회")
  ResponseEntity<ResultResponse<ReadStatus>> findReadStatus(
      @Parameter(description = "Message 읽음 상태 ID") UUID readStatusId
  );
}
