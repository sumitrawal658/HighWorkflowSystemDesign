
package com.highload.service;

import com.highload.model.Notification;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationService {
    Notification sendNotification(Notification notification);
    List<Notification> getNotificationsByUserId(Long userId);
    List<Notification> getNotificationsByStatus(Notification.NotificationStatus status);
    List<Notification> getNotificationsByType(Notification.NotificationType type);
    List<Notification> getRecentNotifications(Long userId, LocalDateTime since);
    List<Notification> getPendingNotificationsDue(LocalDateTime now);
    long countUnreadNotifications(Long userId);
}