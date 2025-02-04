package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JCFChannelRepository implements ChannelRepository {
    private final Map<UUID, Channel> channelData = new HashMap<>();

    private JCFChannelRepository() {
    }

    public static JCFChannelRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final JCFChannelRepository INSTANCE = new JCFChannelRepository();
    }

    @Override
    public Channel saveChannel(Channel channel) {
        channelData.put(channel.getId(), channel);
        return channelData.get(channel.getId());
    }

    @Override
    public Channel findChannelById(UUID channelId) {
        return channelData.get(channelId);
    }

    @Override
    public List<Channel> findAllChannels() {
        return new ArrayList<>(channelData.values());
    }

    @Override
    public void removeChannel(UUID channelId) {
        channelData.remove(channelId);
    }
}
