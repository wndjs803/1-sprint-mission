package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.common.UtilMethod;
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
    public Channel createChannel(UUID channelOwnerId, String name) {
        User channelOwner = jcfUserService.findUserByIdOrThrow(channelOwnerId);
        // 추후 중복 검사
        Channel channel = Channel.of(name, channelOwner);
        channelData.put(channel.getId(), channel);

        return channel;
    }

    @Override
    public Channel findChannelByIdOrThrow(UUID channelId) {
        return Optional.ofNullable(channelData.get(channelId))
                .orElseThrow(() -> new RuntimeException(ErrorMessage.CHANNEL_NOT_FOUND.getMessage()));
    }

    @Override
    public List<Channel> findAllChannels() {
        return new ArrayList<>(channelData.values());
    }

    @Override
    public void updateChannelName(UUID channelOwnerId, UUID channelId, String name) {
        Channel foundChannel = findChannelByIdOrThrow(channelId);
        jcfUserService.findUserByIdOrThrow(channelOwnerId);

        if(foundChannel.isNotOwner(channelOwnerId)) {
            throw new RuntimeException(ErrorMessage.NOT_CHANNEL_CREATOR.getMessage());
        }

        foundChannel.updateName(name);
        foundChannel.updateUpdatedAt(UtilMethod.getCurrentTime());

        channelData.put(channelId, foundChannel);
    }

    @Override
    public void deleteChannel(UUID channelOwnerId, UUID channelId) {
        // 채널 생성자 존재 유무 확인
        jcfUserService.findUserByIdOrThrow(channelOwnerId);
        Channel foundChannel = findChannelByIdOrThrow(channelId);

        if(foundChannel.isNotOwner(channelOwnerId)){
            throw new RuntimeException(ErrorMessage.NOT_CHANNEL_CREATOR.getMessage());
        }

        // 채널 삭제
        channelData.remove(channelId);
    }

    @Override
    public void inviteUsers(UUID channelId, List<User> invitedUserList) {
        Channel foundChannel = findChannelByIdOrThrow(channelId);

        // 유저의 진위 여부에 대한 검증이 필요한가?

        invitedUserList.forEach(user -> foundChannel.addChannelUser(user));
    }

    @Override
    public void leaveUsers(UUID channelId, List<User> leaveUserList) {
        Channel foundChannel = findChannelByIdOrThrow(channelId);

        leaveUserList.forEach(user -> foundChannel.deleteChannelUser(user));
    }
}
