package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.ErrorMessage;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> channelData;

    public JCFChannelService() {
        this.channelData = new HashMap<>();
    }
    @Override
    public void createChannel(String name) {
        // 추후 중복 검사
        Channel channel = new Channel(name, new ArrayList<>(), new ArrayList<>());
        channelData.put(channel.getId(), channel);
    }

    @Override
    public Channel findChannelById(UUID id) {
        return Optional.of(channelData.get(id)).orElseThrow(() -> new RuntimeException(ErrorMessage.CHANNEL_NOT_FOUND));
    }

    @Override
    public List<Channel> findAllChannels() {
        return (List<Channel>) channelData.values();
    }

    @Override
    public void updateChannelName(UUID id, String name) {
        Channel findChannel = findChannelById(id);

        findChannel.updateName(name);

        channelData.put(id, findChannel);
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
