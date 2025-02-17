package com.sprint.mission.discodeit.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    // User
    USER_CREATED(201, "유저 생성에 성공했습니다."),
    USER_UPDATED(200, "유저 정보 수정에 성공했습니다."),
    USER_DELETED(200, "유저 삭제에 성공했습니다."),
    USER_LIST_FETCHED(200, "유저 목록 조회에 성공했습니다."),
    USER_ONLINE_STATUS_UPDATED(200, "유저 온라인 상태 수정에 성공했습니다."),

    // Auth
    LOGIN_SUCCESS(200, "로그인에 성공했습니다."),

    // Channel
    PUBLIC_CHANNEL_CREATED(201, "공개 채널 생성에 성공했습니다."),
    PRIVATE_CHANNEL_CREATED(201, "비공개 채널 생성에 성공했습니다."),
    CHANNEL_UPDATED(200, "채널 정보 수정에 성공했습니다."),
    CHANNEL_DELETED(200, "채널 삭제에 성공했습니다");


    private final int code;
    private final String message;
}
