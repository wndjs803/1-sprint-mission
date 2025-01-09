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
    private final JCFUserService jcfUserService;

    public JCFChannelService(JCFUserService jcfUserService) {
        this.channelData = new HashMap<>();
        this.jcfUserService = jcfUserService;
    }
    @Override
    public void createChannel(UUID channelOwnerId, String name) {
        User channelOwner = jcfUserService.findUserById(channelOwnerId);
        // 추후 중복 검사
        Channel channel = new Channel(name, channelOwner, new ArrayList<>(), new ArrayList<>());
        channelData.put(channel.getId(), channel);
    }

    @Override
    public Channel findChannelById(UUID channelId) {
        return Optional.of(channelData.get(channelId)).orElseThrow(() -> new RuntimeException(ErrorMessage.CHANNEL_NOT_FOUND));
    }

    @Override
    public List<Channel> findAllChannels() {
        return (List<Channel>) channelData.values();
    }

    @Override
    public void updateChannelName(UUID channelOwnerId, UUID channelId, String name) {
        // 채널 주인만 수정 가능하도록

        Channel findChannel = findChannelById(channelId);

        findChannel.updateName(name);

        channelData.put(channelId, findChannel);
    }

    @Override
    public void deleteChannel(UUID channelOwnerId, UUID channelId) {
    }

    @Override
    public void inviteUsers(UUID channelId, List<User> invitedUserList) {
        Channel findChannel = findChannelById(channelId);

        invitedUserList.forEach(user -> findChannel.addChannelUser(user));
    }

    @Override
    public void leaveUsers(UUID channelId, List<User> leaveUserList) {

    }
}
