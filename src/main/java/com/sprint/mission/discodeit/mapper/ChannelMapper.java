package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.channel.response.CreateChannelResponse;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ChannelMapper {
    public Channel toEntity(String name, String description, User channelOwner, ChannelType channelType) {
        return Channel.of(name, description, channelOwner, channelType);
    }

    public CreateChannelResponse toCreateChannelResponse(Channel channel) {
        return new CreateChannelResponse(channel.getId(), channel.getName(), channel.getDescription());
    }
}
