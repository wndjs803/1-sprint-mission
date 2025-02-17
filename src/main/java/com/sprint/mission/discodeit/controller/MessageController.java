package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.message.request.CreateMessageRequest;
import com.sprint.mission.discodeit.dto.message.request.DeleteMessageRequest;
import com.sprint.mission.discodeit.dto.message.request.UpdateMessageRequest;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.global.response.ResultCode;
import com.sprint.mission.discodeit.global.response.ResultResponse;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("")
    public ResponseEntity<ResultResponse<Message>> sendMessage(
            @RequestPart(value = "createMessageRequest") CreateMessageRequest createMessageRequest,
            @RequestPart(value = "multipartFileList") List<MultipartFile> multipartFileList) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultResponse.of(ResultCode.MESSAGE_CREATED,
                        messageService.createMessage(createMessageRequest, multipartFileList)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResultResponse<Message>> updateMessage(
            @PathVariable UUID id,
            @RequestPart(value = "updateMessageRequest") UpdateMessageRequest updateMessageRequest,
            @RequestPart(value = "multipartFileList") List<MultipartFile> multipartFileList) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultResponse.of(ResultCode.MESSAGE_UPDATED,
                        messageService.updateMessage(id, updateMessageRequest, multipartFileList)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResultResponse<Boolean>> deleteMessage(
            @PathVariable UUID id,
            @RequestBody DeleteMessageRequest deleteMessageRequest) {
       messageService.deleteMessage(id, deleteMessageRequest);
       return ResponseEntity.status(HttpStatus.OK)
               .body(ResultResponse.of(ResultCode.CHANNEL_DELETED, true));
    }
}
