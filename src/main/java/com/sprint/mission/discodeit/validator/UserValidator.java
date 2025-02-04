package com.sprint.mission.discodeit.validator;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

    public User validateUserExistsByUserId(UUID userId) {
        return Optional.ofNullable(userRepository.findUserById(userId))
                .orElseThrow(() -> new RuntimeException(ErrorMessage.USER_NOT_FOUND.format(userId)));
    }

    public void validateUserExistsByName(String name) {
        if (userRepository.findUserByName(name) != null) {
            throw new RuntimeException(ErrorMessage.USER_ALREADY_EXIST.format("name: " + name));
        }
    }

    public void validateUserExistsByEmail(String email) {
        if (userRepository.findUserByEmail(email) != null) {
            throw new RuntimeException(ErrorMessage.USER_ALREADY_EXIST.format("email: " + email));
        }
    }

}
