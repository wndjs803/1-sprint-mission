package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message extends BaseEntity {
    private final User sendUser;
    private final Channel channel;
    private String content;

    private Message(User sendUser, Channel channel, String content) {
        super();
        this.sendUser = sendUser;
        this.channel = channel;
        this.content = content;
    }

    public static Message of(User sendUser, Channel channel, String content) {
        return new Message(sendUser, channel, content);
    }

    public User getSendUser() {
        return sendUser;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getContent() {
        return content;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public boolean isNotOwner(UUID sendUserId) {
        return !(this.sendUser.getId().equals(sendUserId));
    }

    @Override
    public String toString() {
        return "Message{" +
                "sendUser=" + sendUser +
                ", channel=" + channel +
                ", content='" + content + '\'' +
                '}';
    }
}
