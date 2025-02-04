package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.common.ErrorMessage;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Channel extends BaseEntity {

    private String name;
    private User channelOwner;
    private final List<User> channelUserList = new ArrayList<>();

    private Channel(String name, User channelOwner) {
        super();
        this.name = name;
        this.channelOwner = channelOwner;
    }

    public static Channel of(String name, User channelOwner) {
        return new Channel(name, channelOwner);
    }

    public void updateChannelOwner(User channelOwner) {
        if (channelOwner == null) {
            throw new IllegalArgumentException(ErrorMessage.CHANNEL_OWNER_NOT_NULL.getMessage());
        }
        this.channelOwner = channelOwner;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void addChannelUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException(ErrorMessage.USER_NOT_NULL.getMessage());
        }
        this.channelUserList.add(user);
    }

    public void deleteChannelUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException(ErrorMessage.USER_NOT_NULL.getMessage());
        }
        this.channelUserList.remove(user);
    }

    public boolean isNotOwner(UUID channelOwnerId) {
        return !(this.channelOwner.getId().equals(channelOwnerId));
    }

    @Override
    public String toString() {
        return "Channel{" +
                "name='" + name + '\'' +
                ", channelOwner=" + channelOwner +
                ", channelUserList=" + channelUserList +
                '}';
    }
}
