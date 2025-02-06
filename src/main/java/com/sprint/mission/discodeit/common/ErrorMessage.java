package com.sprint.mission.discodeit.common;

public enum ErrorMessage {
    // User
    USER_NOT_FOUND("유저를 찾을 수 없습니다. %s"),
    USER_ALREADY_EXIST("이미 유저가 존재합니다. %s"),
    USER_NOT_NULL("유저는 null일 수 없습니다."),

    // Channel
    CHANNEL_NOT_FOUND("채널을 찾을 수 없습니다. %s"),
    NOT_CHANNEL_CREATOR("채널 생성자가 아닙니다. %s"),
    CHANNEL_OWNER_NOT_NULL("채널 소유자는 null일 수 없습니다."),
    CANNOT_UPDATE_PRIVATE_CHANNEL("PRIVATE 채널은 수정할 수 없습니다. %s"),
    CHANNEL_NOT_NULL("채널은 null일 수 없습니다."),

    // Message
    MESSAGE_NOT_FOUND("메세지 찾을 수 없습니다. %s"),
    NOT_MESSAGE_CREATOR("메세지 생성자가 아닙니다. %s"),

    // File
    DIRECTORY_INIT_FAIL("디렉토리 생성에 실패했습니다. path: %s"),
    FILE_WRITE_FAIL("파일 쓰기에 실패했습니다. path: %s, data: %s"),
    FILE_READ_FAIL("파일 읽기에 실패했습니다. path: %s"),
    FILE_REMOVE_FAIL("파일 제거에 실패했습니다. path: %s"),
    FILES_LOAD_FAIL("파일들을 로드하는데 실패했습니다. path: %s"),
    FILE_CONVERSION_FAIL("파일을 바이트 배열로 변환하는 데 실패했습니다. 이유: %s"),

    // ReadStatus
    READSTATUS_NOT_FOUND("readStatus를 찾을 수 없습니다. %s"),

    // BinaryContent
    BINARYCONTENT_NOT_NULL("BinaryContent는 null일 수 없습니다."),

    // UserStatus
    USERSTATUS_ALREADY_EXIST("이미 UserStatus가 존재합니다. %s"),
    USERSTATUS_NOT_FOUND("userStatus를 찾을 수 없습니다. %s");

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
