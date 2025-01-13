package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel createChannel(UUID channelOwnerId, String name);
    Channel findChannelByIdOrThrow(UUID channelId);
    List<Channel> findAllChannels();
    void updateChannelName(UUID channelOwnerId, UUID channelId, String name);
    void deleteChannel(UUID channelOwnerId, UUID channelId);
    void inviteUsers(UUID channelId, List<User> invitedUserList);
    void leaveUsers(UUID channelId, List<User> leaveUserList);
}
