package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class BinaryContent extends BaseEntity {

  private String fileName;
  private Long size;
  private String contentType;
  private final byte[] content;

  public static final BinaryContent EMPTY = new BinaryContent(
      "tempFileName", "Text", new byte[0]);

  private BinaryContent(String fileName, String contentType, byte[] content) {
    this.fileName = fileName;
    this.size = (long) content.length;
    this.contentType = contentType;
    this.content = content;
  }

  public static BinaryContent of(String fileName, String contentType, byte[] content) {
    return new BinaryContent(fileName, contentType, content);
  }
}
