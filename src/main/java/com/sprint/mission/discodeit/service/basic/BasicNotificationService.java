package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.notification.NotificationDto;
import com.sprint.mission.discodeit.entity.Notification;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.jpa.NotificationRepository;
import com.sprint.mission.discodeit.service.NotificationService;
import com.sprint.mission.discodeit.validator.UserValidator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicNotificationService implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserValidator userValidator;

    @Override
    public List<NotificationDto> findAllNotifications(UUID receiverId) {
        User receiver = userValidator.validateUserExistsByUserId(receiverId);
        return notificationRepository.findAllByReceiver(receiver);
    }

    @Override
    public void deleteNotification(UUID notificationId, UUID userId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("알림을 찾을 수 없습니다."));

        if (!notification.getReceiver().getId().equals(userId)) {
            throw new RuntimeException("본인의 알람이 아닙니다.");
        }

        notificationRepository.delete(notification);
    }
}
