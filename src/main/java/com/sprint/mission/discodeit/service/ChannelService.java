package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.channel.request.CreatePrivateChannelRequest;
import com.sprint.mission.discodeit.dto.channel.request.CreatePublicChannelRequest;
import com.sprint.mission.discodeit.dto.channel.response.CreateChannelResponse;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    CreateChannelResponse createPublicChannel(CreatePublicChannelRequest createPublicChannelRequest);

    CreateChannelResponse createPrivateChannel(CreatePrivateChannelRequest createPrivateChannelRequest);

    Channel findChannelByIdOrThrow(UUID channelId);

    List<Channel> findAllChannels();

    Channel updateChannelName(UUID channelOwnerId, UUID channelId, String name);

    void deleteChannel(UUID channelOwnerId, UUID channelId);

    Channel inviteUsers(UUID channelId, List<User> invitedUserList);

    Channel leaveUsers(UUID channelId, List<User> leaveUserList);
}
