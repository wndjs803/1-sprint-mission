package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.channel.request.CreatePrivateChannelRequest;
import com.sprint.mission.discodeit.dto.channel.request.CreatePublicChannelRequest;
import com.sprint.mission.discodeit.dto.channel.request.DeleteChannelRequest;
import com.sprint.mission.discodeit.dto.channel.request.UpdateChannelRequest;
import com.sprint.mission.discodeit.dto.channel.response.CreateChannelResponse;
import com.sprint.mission.discodeit.dto.channel.response.FindChannelResponse;
import com.sprint.mission.discodeit.dto.channel.response.UpdateChannelResponse;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    CreateChannelResponse createPublicChannel(CreatePublicChannelRequest createPublicChannelRequest);

    CreateChannelResponse createPrivateChannel(CreatePrivateChannelRequest createPrivateChannelRequest);

    FindChannelResponse findChannelByIdOrThrow(UUID channelId);

    List<FindChannelResponse> findAllChannelsByUserId(UUID userId);

    UpdateChannelResponse updateChannel(UpdateChannelRequest updateChannelRequest);

    void deleteChannel(DeleteChannelRequest deleteChannelRequest);

    Channel inviteUsers(UUID channelId, List<User> invitedUserList);

    Channel leaveUsers(UUID channelId, List<User> leaveUserList);
}
