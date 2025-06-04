package com.sprint.mission.discodeit.listener;

import com.sprint.mission.discodeit.event.UpdateUserRoleEvent;
import com.sprint.mission.discodeit.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UserEventListener {

    private final NotificationService notificationService;

    @TransactionalEventListener
    public void createNotification(UpdateUserRoleEvent event) {
        notificationService.createNotification(
            event.notificationType(),
            event.user()
        );
    }
}
