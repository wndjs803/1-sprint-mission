package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "binary_contents")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class BinaryContent extends BaseEntity {

  @Column(name = "file_name", nullable = false)
  private String fileName;

  @Column(nullable = false)
  private Long size;

  @Column(name = "content_type", nullable = false, length = 100)
  private String contentType;

  @Column(name = "bytes", nullable = false)
  private byte[] content;

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
