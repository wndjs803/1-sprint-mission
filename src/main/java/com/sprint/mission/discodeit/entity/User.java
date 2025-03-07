package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class User extends BaseUpdatableEntity {

  @Column(name = "username", nullable = false, unique = true, length = 50)
  private String name;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @Column(nullable = false, length = 60)
  private String password;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "profile_id") // table의 'profile_id' 컬럼
  private BinaryContent profileImage;

  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
  // UserStatus의 'user'필드
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

  public void updateUserStatus(UserStatus userStatus) {
    this.userStatus = userStatus;
  }

  public void updateUserInfo(String name, String email, String password) {
    this.updateName(name);
    this.updateEmail(email);
    this.updatePassword(password);
  }
}
