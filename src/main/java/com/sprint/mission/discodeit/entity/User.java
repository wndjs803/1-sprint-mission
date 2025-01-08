package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class User {
    private final UUID id;
    private String name;
    private String nickname;
    private String email;
    private String password;
    private String profileImageUrl;
    private boolean active;
    private List<Channel> channelList;
    private final Long createdAt;
    private Long updatedAt;

    public User(String name, String nickname, String email, String password,
                String profileImageUrl, boolean active, List<Channel> channelList) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.active = active;
        this.channelList = channelList;
        this.createdAt = Instant.now().toEpochMilli();
        this.updatedAt = 0L;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public boolean isActive() {
        return active;
    }

    public List<Channel> getChannelList() {
        return channelList;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
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

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void updateActive(){
        this.active = !this.active;
    }

    public void updateChannelList(List<Channel> channelList) {
        this.channelList = channelList;
    }

    public void updateUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
