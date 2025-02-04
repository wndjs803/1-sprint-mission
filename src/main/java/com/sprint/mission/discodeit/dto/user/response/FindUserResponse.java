package com.sprint.mission.discodeit.dto.user.response;

import com.sprint.mission.discodeit.entity.BinaryContent;
import org.springframework.web.multipart.MultipartFile;

public record FindUserResponse(
        String name,
        String nickname,
        String email,
        MultipartFile profileImage,
        boolean isOnline
) {
}
