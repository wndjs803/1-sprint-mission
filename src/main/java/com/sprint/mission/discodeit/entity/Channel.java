package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Channel {
    private final UUID id;
    private String name;
    private List<User> channelUserList;
    private List<Message> messageList;
    private final Long createdAt;
    private Long updatedAt;

    public Channel(String name, List<User> channelUserList, List<Message> messageList) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.channelUserList = channelUserList;
        this.messageList = messageList;
        this.createdAt = Instant.now().toEpochMilli();
        this.updatedAt = 0L;
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

    public List<Message> getMessageList() {
        return messageList;
    }

    public void updateMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void UpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
