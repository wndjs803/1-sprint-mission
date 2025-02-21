package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.controller.api.ChannelApi;
import com.sprint.mission.discodeit.dto.channel.request.CreatePrivateChannelRequest;
import com.sprint.mission.discodeit.dto.channel.request.CreatePublicChannelRequest;
import com.sprint.mission.discodeit.dto.channel.request.UpdateChannelRequest;
import com.sprint.mission.discodeit.dto.channel.response.FindChannelResponse;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.global.response.ResultCode;
import com.sprint.mission.discodeit.global.response.ResultResponse;
import com.sprint.mission.discodeit.service.ChannelService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/channels")
public class ChannelController implements ChannelApi {

  private final ChannelService channelService;

  @RequestMapping(value = "/public", method = RequestMethod.POST)
  public ResponseEntity<ResultResponse<Channel>> createPublicChannel(
      @RequestBody CreatePublicChannelRequest createPublicChannelRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ResultResponse.of(ResultCode.PUBLIC_CHANNEL_CREATED,
            channelService.createPublicChannel(createPublicChannelRequest)));
  }

  @RequestMapping(value = "/private", method = RequestMethod.POST)
  public ResponseEntity<ResultResponse<Channel>> createPrivateChannel(
      @RequestBody CreatePrivateChannelRequest createPrivateChannelRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ResultResponse.of(ResultCode.PRIVATE_CHANNEL_CREATED,
            channelService.createPrivateChannel(createPrivateChannelRequest)));
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
  public ResponseEntity<ResultResponse<Channel>> updateChannel(
      @PathVariable UUID id,
      @RequestBody UpdateChannelRequest updateChannelRequest) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ResultResponse.of(ResultCode.CHANNEL_UPDATED,
            channelService.updateChannel(id, updateChannelRequest)));
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<ResultResponse<Boolean>> deleteChannel(
      @PathVariable UUID id,
      @RequestParam UUID channelOwnerId) {
    channelService.deleteChannel(id, channelOwnerId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(ResultResponse.of(ResultCode.CHANNEL_DELETED, true));
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<ResultResponse<List<FindChannelResponse>>> findUserAccessibleChannels(
      @RequestParam UUID userId) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ResultResponse.of(ResultCode.CHANNEL_LIST_FETCHED,
            channelService.findAllChannelsByUserId(userId)));
  }
}