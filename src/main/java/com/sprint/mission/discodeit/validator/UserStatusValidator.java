package com.sprint.mission.discodeit.validator;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.common.error.execption.userStatus.UserStatusAlreadyExistException;
import com.sprint.mission.discodeit.common.error.execption.userStatus.UserStatusNotFoundException;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStatusValidator {

  private final UserStatusRepository userStatusRepository;

  public UserStatus validateUserStatusExistsById(UUID userStatusId) {
    return userStatusRepository.findUserStatusById(userStatusId)
        .orElseThrow(() -> new UserStatusNotFoundException("id: " + userStatusId));
  }

  public UserStatus validateUserStatusExistsByUser(User user) {
    return userStatusRepository.findUserStatusByUser(user)
        .orElseThrow(() -> new UserStatusNotFoundException("id: " + user.getId()));
  }

  public void validateDuplicateByUser(User user) {
    if (userStatusRepository.findUserStatusByUser(user).isPresent()) {
      throw new UserStatusAlreadyExistException("userId: " + user.getId());
    }
  }
}
