package com.sprint.mission.discodeit.common;

public enum ErrorMessage {
    // User
    USER_NOT_FOUND("유저를 찾을 수 없습니다."),

    // Channel
    CHANNEL_NOT_FOUND("채널을 찾을 수 없습니다."),
    NOT_CHANNEL_CREATOR("채널 생성자가 아닙니다."),
    CHANNEL_OWNER_NOT_NULL("채널 소유자는 null일 수 없습니다."),
    USER_NOT_NULL("유저는 null일 수 없습니다."),


    // Message
    MESSAGE_NOT_FOUND ("메세지 찾을 수 없습니다."),
    NOT_MESSAGE_CREATOR("메세지 생성자가 아닙니다.");

    private String msg;

    ErrorMessage(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return this.msg;
    }
}
