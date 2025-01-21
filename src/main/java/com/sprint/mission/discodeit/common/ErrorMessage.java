package com.sprint.mission.discodeit.common;

public enum ErrorMessage {
    // User
    USER_NOT_FOUND("유저를 찾을 수 없습니다. id: %s"),

    // Channel
    CHANNEL_NOT_FOUND("채널을 찾을 수 없습니다. id: %s"),
    NOT_CHANNEL_CREATOR("채널 생성자가 아닙니다. id: %s"),
    CHANNEL_OWNER_NOT_NULL("채널 소유자는 null일 수 없습니다."),
    USER_NOT_NULL("유저는 null일 수 없습니다."),


    // Message
    MESSAGE_NOT_FOUND("메세지 찾을 수 없습니다."),
    NOT_MESSAGE_CREATOR("메세지 생성자가 아닙니다."),

    // File
    DIRECTORY_INIT_FAIL("디렉토리 생성에 실패했습니다. path: %s"),
    FILE_WRITE_FAIL("파일 쓰기에 실패했습니다. path: %s, data: %s"),
    FILE_READ_FAIL("파일 읽기에 실패했습니다. path: %s"),
    FILE_REMOVE_FAIL("파일 제거에 실패했습니다. path: %s"),
    FILES_LOAD_FAIL("파일들을 로드하는데 실패했습니다. path: %s");

    private final String msg;

    ErrorMessage(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return this.msg;
    }
    public String format(Object... args) {
        return String.format(msg, args);
    }
}
