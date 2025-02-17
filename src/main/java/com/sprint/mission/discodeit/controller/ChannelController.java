package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.channel.request.CreatePrivateChannelRequest;
import com.sprint.mission.discodeit.dto.channel.request.CreatePublicChannelRequest;
import com.sprint.mission.discodeit.dto.channel.request.DeleteChannelRequest;
import com.sprint.mission.discodeit.dto.channel.request.UpdateChannelRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.global.response.ResultCode;
import com.sprint.mission.discodeit.global.response.ResultResponse;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("channels")
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping("/public")
    public ResponseEntity<ResultResponse<Channel>> createPublicChannel(
            @RequestBody CreatePublicChannelRequest createPublicChannelRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResultResponse.of(ResultCode.PUBLIC_CHANNEL_CREATED,
                                channelService.createPublicChannel(createPublicChannelRequest)));
    }

    @PostMapping("/private")
    public ResponseEntity<ResultResponse<Channel>> createPrivateChannel(
            @RequestBody CreatePrivateChannelRequest createPrivateChannelRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultResponse.of(ResultCode.PRIVATE_CHANNEL_CREATED,
                        channelService.createPrivateChannel(createPrivateChannelRequest)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResultResponse<Channel>> updateChannel(
            @PathVariable UUID id,
            @RequestBody UpdateChannelRequest updateChannelRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResultResponse.of(ResultCode.CHANNEL_UPDATED,
                        channelService.updateChannel(id, updateChannelRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponse<Boolean>> deleteChannel(
            @PathVariable UUID id,
            @RequestBody DeleteChannelRequest deleteChannelRequest) {
        channelService.deleteChannel(id, deleteChannelRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResultResponse.of(ResultCode.CHANNEL_DELETED, true));
    }
}