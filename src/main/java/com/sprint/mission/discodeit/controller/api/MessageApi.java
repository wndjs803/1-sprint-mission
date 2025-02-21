package com.sprint.mission.discodeit.controller.api;

import com.sprint.mission.discodeit.dto.message.request.CreateMessageRequest;
import com.sprint.mission.discodeit.dto.message.request.UpdateMessageRequest;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.global.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Message", description = "Message API")
public interface MessageApi {

  @Operation(summary = "Message 생성")
  ResponseEntity<ResultResponse<Message>> sendMessage(
      @Parameter(
          description = "Message 생성 정보",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      ) CreateMessageRequest messageCreateRequest,
      @Parameter(
          description = "Message 첨부 파일들",
          content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
      ) List<MultipartFile> multipartFileList
  );

  @Operation(summary = "Message 내용 수정")
  ResponseEntity<ResultResponse<Message>> updateMessage(
      @Parameter(description = "수정할 Message ID") UUID messageId,
      @Parameter(description = "수정할 Message 내용") UpdateMessageRequest request,
      @Parameter(
          description = "Message 첨부 파일들",
          content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
      ) List<MultipartFile> multipartFileList
  );

  @Operation(summary = "Message 삭제")
  ResponseEntity<ResultResponse<Boolean>> deleteMessage(
      @Parameter(description = "삭제할 Message ID") UUID messageId,
      @Parameter(description = "삭제할 Message 생성자 ID") UUID senderId
  );

  @Operation(summary = "Channel의 Message 목록 조회")
  ResponseEntity<ResultResponse<List<Message>>> findMessagesByChannel(
      @Parameter(description = "조회할 Channel ID") UUID channelId
  );
}
