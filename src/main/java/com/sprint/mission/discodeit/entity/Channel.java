package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.global.UtilMethod;

import java.util.List;
import java.util.UUID;

public class Channel {
    private final UUID id;
    private String name;
    private User channelOwner;
    private final List<User> channelUserList;
    private final Long createdAt;
    private Long updatedAt;

    public Channel(String name, User channelOwner, List<User> channelUserList) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.channelOwner = channelOwner;
        this.channelUserList = channelUserList;
        this.createdAt = UtilMethod.getCurrentTime();
        this.updatedAt = 0L;
    }

    public User getChannelOwner() {
        return channelOwner;
    }

    public void updateChannelOwner(User channelOwner) {
        this.channelOwner = channelOwner;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public List<User> getChannelUserList() {
        return channelUserList;
    }

    public void addChannelUser(User user){
        this.channelUserList.add(user);
        user.addChannel(this);
    }

    public void deleteChannelUser(User user){
        this.channelUserList.remove(user);
        user.deleteChannel(this);
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void updateUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
