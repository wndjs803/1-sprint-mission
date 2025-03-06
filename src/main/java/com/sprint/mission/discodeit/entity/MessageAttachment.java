package com.sprint.mission.discodeit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "message_attachments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageAttachment {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "message_id")
  private Message message;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "attachment_id")
  private BinaryContent attachment;

  private MessageAttachment(Message message, BinaryContent attachment) {
    this.message = message;
    this.attachment = attachment;
  }

  public static MessageAttachment of(Message message, BinaryContent attachment) {
    return new MessageAttachment(message, attachment);
  }
}
