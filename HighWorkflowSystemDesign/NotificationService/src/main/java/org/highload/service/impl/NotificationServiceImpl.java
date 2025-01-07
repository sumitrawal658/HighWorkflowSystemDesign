package com.highload.service.impl;

import com.highload.model.Notification;
import com.highload.repository.NotificationRepository;
import com.highload.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification sendNotification(Notification notification) {
        if (notification == null) {
            throw new ValidationException("Notification object cannot be null.");
        }
        if (notification.getUserId() == null || notification.getUserId() <= 0) {
            throw new ValidationException("User ID must be a positive number.");
        }
        if (notification.getMessage() == null || notification.getMessage().trim().isEmpty()) {
            throw new ValidationException("Notification message cannot be null or empty.");
        }
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new ValidationException("User ID must be a positive number.");
        }
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getNotificationsByStatus(Notification.NotificationStatus status) {
        if (status == null) {
            throw new ValidationException("Notification status cannot be null.");
        }
        return notificationRepository.findByStatus(status);
    }

    @Override
    public List<Notification> getNotificationsByType(Notification.NotificationType type) {
        if (type == null) {
            throw new ValidationException("Notification type cannot be null.");
        }
        return notificationRepository.findByType(type);
    }

    @Override
    public List<Notification> getRecentNotifications(Long userId, LocalDateTime since) {
        if (userId == null || userId <= 0) {
            throw new ValidationException("User ID must be a positive number.");
        }
        if (since == null) {
            throw new ValidationException("Timestamp 'since' cannot be null.");
        }
        return notificationRepository.findRecentNotifications(userId, since);
    }

    @Override
    public List<Notification> getPendingNotificationsDue(LocalDateTime now) {
        if (now == null) {
            throw new ValidationException("Current time 'now' cannot be null.");
        }
        return notificationRepository.findPendingNotificationsDue(now);
    }

    @Override
    public long countUnreadNotifications(Long userId) {
        if (userId == null || userId <= 0) {
            throw new ValidationException("User ID must be a positive number.");
        }
        return notificationRepository.countUnreadNotifications(userId);
    }
}