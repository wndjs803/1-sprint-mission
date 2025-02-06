package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.common.TimeUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Message extends BaseEntity {

    private final User sendUser;
    private final Channel channel;
    private String content;
    private final List<BinaryContent> binaryContentList = new ArrayList<>();

    private Message(User sendUser, Channel channel, String content) {
        super();
        this.sendUser = sendUser;
        this.channel = channel;
        this.content = content;
    }

    public static Message of(User sendUser, Channel channel, String content) {
        if (sendUser == null) {
            throw new IllegalArgumentException(ErrorMessage.USER_NOT_NULL.getMessage());
        }
        if (channel == null) {
            throw new IllegalArgumentException(ErrorMessage.CHANNEL_NOT_NULL.getMessage());
        }
        return new Message(sendUser, channel, content);
    }

    public void updateContent(String content) {
        this.content = content;
        this.updateUpdatedAt(TimeUtil.getCurrentTime());
    }

    public boolean isNotOwner(UUID sendUserId) {
        return !(this.sendUser.getId().equals(sendUserId));
    }

    public void addBinaryContent(BinaryContent binaryContent) {
        if (binaryContent == null) {
            throw new IllegalArgumentException(ErrorMessage.BINARYCONTENT_NOT_NULL.getMessage());
        }
        this.binaryContentList.add(binaryContent);
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
