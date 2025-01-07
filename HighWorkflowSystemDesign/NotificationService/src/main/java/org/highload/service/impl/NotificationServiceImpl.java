package com.highload.service.impl;

import com.highload.model.Notification;
import com.highload.service.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final List<Notification> notifications = new ArrayList<>();

    @Override
    public Notification sendNotification(Notification notification) {
        notification.setCreatedAt(LocalDateTime.now());
        notification.setStatus(Notification.NotificationStatus.PENDING);
        notifications.add(notification);
        return notification;
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        return notifications.stream()
                .filter(notification -> notification.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> getNotificationsByStatus(Notification.NotificationStatus status) {
        return notifications.stream()
                .filter(notification -> notification.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> getNotificationsByType(Notification.NotificationType type) {
        return notifications.stream()
                .filter(notification -> notification.getType() == type)
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> getRecentNotifications(Long userId, LocalDateTime since) {
        return notifications.stream()
                .filter(notification -> notification.getUserId().equals(userId) && notification.getCreatedAt().isAfter(since))
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> getPendingNotificationsDue(LocalDateTime now) {
        return notifications.stream()
                .filter(notification -> notification.getStatus() == Notification.NotificationStatus.PENDING && notification.getCreatedAt().isBefore(now))
                .collect(Collectors.toList());
    }

    @Override
    public long countUnreadNotifications(Long userId) {
        return notifications.stream()
                .filter(notification -> notification.getUserId().equals(userId) && notification.getStatus() == Notification.NotificationStatus.PENDING)
                .count();
    }
}