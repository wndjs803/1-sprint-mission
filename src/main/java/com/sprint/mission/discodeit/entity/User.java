package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class User extends BaseEntity implements Serializable {

    private String name;
    private String nickname;
    private String email;
    private String password;
    private BinaryContent profileImage;
    private boolean active;

    private User(String name, String nickname, String email, String password, boolean active) {
        super();
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.active = active;
    }

    public static User of(String name, String nickname, String email, String password, boolean active) {
        return new User(name, nickname, email, password, active);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateProfileImage(BinaryContent profileImage) {
        this.profileImage = profileImage;
    }

    public void updateActive() {
        this.active = !this.active;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profileImage=" + profileImage +
                ", active=" + active +
                '}';
    }
}
