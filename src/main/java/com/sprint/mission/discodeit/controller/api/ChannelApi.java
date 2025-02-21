package com.sprint.mission.discodeit.controller.api;

import com.sprint.mission.discodeit.dto.channel.request.CreatePrivateChannelRequest;
import com.sprint.mission.discodeit.dto.channel.request.CreatePublicChannelRequest;
import com.sprint.mission.discodeit.dto.channel.request.UpdateChannelRequest;
import com.sprint.mission.discodeit.dto.channel.response.FindChannelResponse;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.global.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

@Tag(name = "Channel", description = "Channel API")
public interface ChannelApi {

  @Operation(summary = "Public Channel 생성")
  ResponseEntity<ResultResponse<Channel>> createPublicChannel(
      @Parameter(description = "Public Channel 생성 정보") CreatePublicChannelRequest request
  );

  @Operation(summary = "Private Channel 생성")
  ResponseEntity<ResultResponse<Channel>> createPrivateChannel(
      @Parameter(description = "Private Channel 생성 정보") CreatePrivateChannelRequest request
  );

  @Operation(summary = "Channel 정보 수정")
  ResponseEntity<ResultResponse<Channel>> updateChannel(
      @Parameter(description = "수정할 Channel ID") UUID channelId,
      @Parameter(description = "수정할 Channel 정보") UpdateChannelRequest request
  );

  @Operation(summary = "Channel 삭제")
  ResponseEntity<ResultResponse<Boolean>> deleteChannel(
      @Parameter(description = "삭제할 Channel ID") UUID channelId,
      @Parameter(description = "삭제할 Channel의 생성자 ID") UUID channelOwnerId
  );

  @Operation(summary = "User가 참여 중인 Channel 목록 조회")
  ResponseEntity<ResultResponse<List<FindChannelResponse>>> findUserAccessibleChannels(
      @Parameter(description = "조회할 User ID") UUID userId
  );
}
