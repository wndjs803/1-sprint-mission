package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;

@Getter
public class User extends BaseEntity implements Serializable {

  private String name;
  private String nickname;
  private String email;
  private String password;
  private BinaryContent profileImage = BinaryContent.EMPTY;

  private User(String name, String nickname, String email, String password) {
    super();
    this.name = name;
    this.nickname = nickname;
    this.email = email;
    this.password = password;
  }

  public static User of(String name, String nickname, String email, String password) {
    return new User(name, nickname, email, password);
  }

  public void updateName(String name) {
    this.name = name;
    this.updateUpdatedAt();
  }

  public void updateNickname(String nickname) {
    this.nickname = nickname;
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

  public void updateUserInfo(String name, String nickname, String email, String password) {
    this.updateName(name);
    this.updateNickname(nickname);
    this.updateEmail(email);
    this.updatePassword(password);
  }

  @Override
  public String toString() {
    return "User{" +
        "name='" + name + '\'' +
        ", nickname='" + nickname + '\'' +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", profileImage=" + profileImage +
        '}';
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    if (!super.equals(object)) {
      return false;
    }
    User user = (User) object;
    return Objects.equals(name, user.name) && Objects.equals(nickname, user.nickname)
        && Objects.equals(email, user.email) && Objects.equals(password, user.password)
        && Objects.equals(profileImage, user.profileImage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name, nickname, email, password, profileImage);
  }
}
