package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import com.sprint.mission.discodeit.global.error.execption.bianryContent.BinaryContentNotNullException;
import com.sprint.mission.discodeit.global.error.execption.channel.ChannelNotNullException;
import com.sprint.mission.discodeit.global.error.execption.user.UserNotNullException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "messages")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Message extends BaseUpdatableEntity {

  @ManyToOne(fetch = FetchType.LAZY) // on delete set null을 기능적으로 지원하지 않는 듯 하다.
  @JoinColumn(name = "author_id")
  private User sender;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  @JoinColumn(name = "channel_id", nullable = false)
  private Channel channel;

  @Column(columnDefinition = "TEXT")
  private String content;

  @OneToMany
  @JoinColumn(name = "message_id")
  private final List<MessageAttachments> attachmentsList = new ArrayList<>();

  private Message(User sender, Channel channel, String content) {
    super();
    this.sender = sender;
    this.channel = channel;
    this.content = content;
  }

  public static Message of(User sender, Channel channel, String content) {
    if (sender == null) {
      throw new UserNotNullException();
    }
    if (channel == null) {
      throw new ChannelNotNullException();
    }
    return new Message(sender, channel, content);
  }

  public void updateContent(String content) {
    this.content = content;
    this.updateUpdatedAt();
  }

  public boolean isNotOwner(UUID senderId) {
    return !(this.sender.getId().equals(senderId));
  }

  public void addBinaryContent(BinaryContent binaryContent) {
    if (binaryContent == null) {
      throw new BinaryContentNotNullException();
    }
//    this.binaryContentList.add(binaryContent);
    this.updateUpdatedAt();
  }

  public void deleteBinaryContent(BinaryContent binaryContent) {
    if (binaryContent == null) {
      throw new BinaryContentNotNullException();
    }
//    this.binaryContentList.remove(binaryContent);
    this.updateUpdatedAt();
  }
}
