package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.channel.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.request.CreatePrivateChannelRequest;
import com.sprint.mission.discodeit.dto.channel.request.CreatePublicChannelRequest;
import com.sprint.mission.discodeit.dto.channel.request.UpdateChannelRequest;
import com.sprint.mission.discodeit.dto.channel.response.FindChannelResponse;
import java.util.List;
import java.util.UUID;

public interface ChannelService {

  ChannelDto createPublicChannel(CreatePublicChannelRequest createPublicChannelRequest);

  ChannelDto createPrivateChannel(CreatePrivateChannelRequest createPrivateChannelRequest);

  FindChannelResponse findChannelByIdOrThrow(UUID channelId);

  List<FindChannelResponse> findAllChannelsByUserId(UUID userId);

  ChannelDto updateChannel(UUID channelId, UpdateChannelRequest updateChannelRequest);

  void deleteChannel(UUID channelId);
}
