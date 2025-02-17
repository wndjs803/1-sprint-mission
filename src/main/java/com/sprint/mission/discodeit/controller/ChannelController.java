package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.channel.request.CreatePrivateChannelRequest;
import com.sprint.mission.discodeit.dto.channel.request.CreatePublicChannelRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.global.response.ResultCode;
import com.sprint.mission.discodeit.global.response.ResultResponse;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}