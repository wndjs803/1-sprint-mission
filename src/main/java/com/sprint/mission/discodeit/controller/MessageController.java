package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.controller.api.MessageApi;
import com.sprint.mission.discodeit.dto.message.request.CreateMessageRequest;
import com.sprint.mission.discodeit.dto.message.request.UpdateMessageRequest;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.global.response.ResultCode;
import com.sprint.mission.discodeit.global.response.ResultResponse;
import com.sprint.mission.discodeit.service.MessageService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController implements MessageApi {

  private final MessageService messageService;

  @RequestMapping(value = "", method = RequestMethod.POST, consumes = {
      MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<ResultResponse<Message>> sendMessage(
      @RequestPart(value = "createMessageRequest") CreateMessageRequest createMessageRequest,
      @RequestPart(value = "multipartFileList") List<MultipartFile> multipartFileList) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ResultResponse.of(ResultCode.MESSAGE_CREATED,
            messageService.createMessage(createMessageRequest, multipartFileList)));
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, consumes = {
      MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<ResultResponse<Message>> updateMessage(
      @PathVariable UUID id,
      @RequestPart(value = "updateMessageRequest") UpdateMessageRequest updateMessageRequest,
      @RequestPart(value = "multipartFileList") List<MultipartFile> multipartFileList) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ResultResponse.of(ResultCode.MESSAGE_UPDATED,
            messageService.updateMessage(id, updateMessageRequest, multipartFileList)));
  }

  @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
  public ResponseEntity<ResultResponse<Boolean>> deleteMessage(
      @PathVariable UUID id, @RequestParam UUID senderId) {
    messageService.deleteMessage(id, senderId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(ResultResponse.of(ResultCode.MESSAGE_DELETED, true));
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<ResultResponse<List<Message>>> findMessagesByChannel(
      @RequestParam UUID channelId) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ResultResponse.of(ResultCode.MESSAGE_LIST_FETCHED,
            messageService.findAllMessagesByChannelId(channelId)));
  }
}
