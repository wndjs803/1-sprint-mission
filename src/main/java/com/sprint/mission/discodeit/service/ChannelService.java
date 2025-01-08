package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    void createChannel(String name);
    Channel findChannelById(UUID id);
    List<Channel> findAllChannels();
    void updateChannelName(UUID id, String name);
    void deleteChannel(UUID id);
    void inviteUsers(UUID id, List<User> invitedUserList);
    void leaveUsers(UUID id, List<User> leaveUserList);
}
