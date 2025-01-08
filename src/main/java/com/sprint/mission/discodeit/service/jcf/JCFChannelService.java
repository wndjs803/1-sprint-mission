package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> channelData;

    public JCFChannelService() {
        this.channelData = new HashMap<>();
    }
    @Override
    public void createChannel(String name) {
        Channel channel = new Channel(name, new ArrayList<>(), new ArrayList<>());
        channelData.put(channel.getId(), channel);
    }

    @Override
    public Channel findChannelById(UUID id) {
        return null;
    }

    @Override
    public List<Channel> findAllChannels() {
        return null;
    }

    @Override
    public void updateChannelName(UUID id, String name) {

    }

    @Override
    public void deleteChannel(UUID id) {

    }

    @Override
    public void inviteUsers(UUID id, List<User> invitedUserList) {

    }

    @Override
    public void leaveUsers(UUID id, List<User> leaveUserList) {

    }
}
