package com.sprint.mission.discodeit.validator;

import com.sprint.mission.discodeit.global.error.ErrorCode;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.global.error.execption.user.UserAlreadyExistException;
import com.sprint.mission.discodeit.global.error.execption.user.UserNotFoundException;
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
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND.format("id: " + userId)));
    }

    public void validateDuplicateByName(String name) {
        if (userRepository.findUserByName(name).isPresent()) {
            throw new UserAlreadyExistException(ErrorCode.USER_ALREADY_EXIST.format("name: " + name));
        }

    }

    public void validateDuplicateUserByEmail(String email) {
        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new UserAlreadyExistException(ErrorCode.USER_ALREADY_EXIST.format("email: " + email));
        }
    }

    public User validateUserExistsByNameAndPassword(String name, String password) {
        return userRepository.findUserByNameAndPassword(name, password)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND.format(
                        "name: " + name + " password: " + password)));
    }

}
