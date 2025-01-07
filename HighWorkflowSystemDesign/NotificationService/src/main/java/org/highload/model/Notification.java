package com.highload.model;

import java.time.LocalDateTime;

public class Notification {

    public enum NotificationStatus {
        PENDING,
        SENT,
        FAILED
    }

    private Long id;
    private Long userId;
    private String message;
    private NotificationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;

    // Constructors
    public Notification() {}

    public Notification(Long id, Long userId, String message, NotificationStatus status, LocalDateTime createdAt, LocalDateTime sentAt) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
        this.sentAt = sentAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}