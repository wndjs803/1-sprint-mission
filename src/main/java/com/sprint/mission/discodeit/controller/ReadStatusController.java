package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.readStatus.request.CreateReadStatusRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.global.response.ResultCode;
import com.sprint.mission.discodeit.global.response.ResultResponse;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/read-status")
public class ReadStatusController {

    private final ReadStatusService readStatusService;

    @PostMapping("")
    public ResponseEntity<ResultResponse<ReadStatus>> createReadStatus(
            @RequestBody CreateReadStatusRequest createReadStatusRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultResponse.of(ResultCode.READSTATUS_CREATED,
                        readStatusService.createReadStatus(createReadStatusRequest)));
    }

    @PatchMapping("{id}")
    public ResponseEntity<ResultResponse<ReadStatus>> updateReadStatus(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResultResponse.of(ResultCode.READSTATUS_UPDATED,
                        readStatusService.updateReadStatus(id)));
    }
}
