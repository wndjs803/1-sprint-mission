package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.user.request.LoginRequest;
import com.sprint.mission.discodeit.dto.user.response.LoginResponse;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.AuthService;
import com.sprint.mission.discodeit.validator.UserStatusValidator;
import com.sprint.mission.discodeit.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class BasicAuthService implements AuthService {

    private final UserStatusRepository userStatusRepository;
    private final UserStatusValidator userStatusValidator;
    private final UserValidator userValidator;
    private final UserMapper userMapper;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userValidator.validateUserExistsByNameAndPassword(loginRequest.name(), loginRequest.password());

        // UserStatus 로그인 여부 변경
        UserStatus userStatus = userStatusValidator.validateUserStatusExistsByUser(user);
        userStatus.updateLoginAt();
        userStatus.updateOnline();
        userStatusRepository.saveUserStatus(userStatus);

        return userMapper.toLoginResponse(user);
    }
}
