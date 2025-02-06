package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.common.TimeUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Channel extends BaseEntity {

    private String name;
    private String description;
    private User channelOwner;
    private final List<User> channelUserList = new ArrayList<>();
    private final ChannelType channelType;

    private Channel(String name, String description, User channelOwner, ChannelType channelType) {
        super();
        this.name = name;
        this.description = description;
        this.channelOwner = channelOwner;
        this.channelType = channelType;
    }

    public static Channel of(String name, String description, User channelOwner, ChannelType channelType) {
        if (channelOwner == null) {
            throw new IllegalArgumentException(ErrorMessage.CHANNEL_OWNER_NOT_NULL.getMessage());
        }
        return new Channel(name, description, channelOwner, channelType);
    }

    public void updateChannelOwner(User channelOwner) {
        if (channelOwner == null) {
            throw new IllegalArgumentException(ErrorMessage.CHANNEL_OWNER_NOT_NULL.getMessage());
        }
        this.channelOwner = channelOwner;
        this.updateUpdatedAt(TimeUtil.getCurrentTime());
    }

    public void updateName(String name) {
        this.name = name;
        this.updateUpdatedAt(TimeUtil.getCurrentTime());
    }

    public void updateDescription(String description) {
        this.description = description;
        this.updateUpdatedAt(TimeUtil.getCurrentTime());
    }

    public void addChannelUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException(ErrorMessage.USER_NOT_NULL.getMessage());
        }
        this.channelUserList.add(user);
    }

    public void removeUserFromChannel(User user) {
        if (user == null) {
            throw new IllegalArgumentException(ErrorMessage.USER_NOT_NULL.getMessage());
        }
        this.channelUserList.remove(user);
    }

    public boolean isNotOwner(UUID channelOwnerId) {
        return !(this.channelOwner.getId().equals(channelOwnerId));
    }

    public boolean isPrivate() {
        return this.channelType == ChannelType.PRIVATE;
    }

    public boolean isPublic() {
        return this.channelType == ChannelType.PUBLIC;
    }

    public boolean isUserInChannel(User user) {
        return (this.channelUserList.contains(user) || channelOwner.equals(user));
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
