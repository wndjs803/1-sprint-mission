package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class User extends BaseUpdatableEntity {

  private String name;
  private String email;
  private String password;
  private BinaryContent profileImage = BinaryContent.EMPTY;
  private UserStatus userStatus;

  private User(String name, String email, String password) {
    super();
    this.name = name;
    this.email = email;
    this.password = password;
  }

  public static User of(String name, String email, String password) {
    return new User(name, email, password);
  }

  public void updateName(String name) {
    this.name = name;
    this.updateUpdatedAt();
  }

  public void updateEmail(String email) {
    this.email = email;
    this.updateUpdatedAt();
  }

  public void updatePassword(String password) {
    this.password = password;
    this.updateUpdatedAt();
  }

  public void updateProfileImage(BinaryContent profileImage) {
    this.profileImage = profileImage;
    this.updateUpdatedAt();
  }

  public void updateUserInfo(String name, String email, String password) {
    this.updateName(name);
    this.updateEmail(email);
    this.updatePassword(password);
  }
}
