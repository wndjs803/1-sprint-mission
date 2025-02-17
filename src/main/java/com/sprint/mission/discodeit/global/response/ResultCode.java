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

    // AUth
    LOGIN_SUCCESS(200, "로그인에 성공했습니다.");

    private final int code;
    private final String message;
}
