package com.acme.legacy.service;

import java.util.*;
import java.time.LocalDateTime;

public class NotificationService {
    
    private Map<Long, List<Notification>> userNotifications = new HashMap<>();
    private Queue<Notification> pendingNotifications = new LinkedList<>();
    
    public enum NotificationType {
        EMAIL, SMS, PUSH, IN_APP
    }
    
    public enum NotificationPriority {
        LOW, MEDIUM, HIGH, URGENT
    }
    
    public Notification sendNotification(Long userId, String message, NotificationType type, NotificationPriority priority) {
        Notification notification = new Notification();
        notification.setId(System.currentTimeMillis());
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setType(type);
        notification.setPriority(priority);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);
        notification.setSent(false);
        
        pendingNotifications.add(notification);
        userNotifications.computeIfAbsent(userId, k -> new ArrayList<>()).add(notification);
        
        return notification;
    }
    
    public void processPendingNotifications() {
        while (!pendingNotifications.isEmpty()) {
            Notification notification = pendingNotifications.poll();
            boolean sent = sendToGateway(notification);
            notification.setSent(sent);
            if (sent) {
                notification.setSentAt(LocalDateTime.now());
            }
        }
    }
    
    private boolean sendToGateway(Notification notification) {
        return true;
    }
    
    public List<Notification> getUserNotifications(Long userId) {
        return userNotifications.getOrDefault(userId, new ArrayList<>());
    }
    
    public void markAsRead(Long notificationId, Long userId) {
        List<Notification> notifications = userNotifications.get(userId);
        if (notifications != null) {
            for (Notification notification : notifications) {
                if (notification.getId().equals(notificationId)) {
                    notification.setRead(true);
                    notification.setReadAt(LocalDateTime.now());
                    break;
                }
            }
        }
    }
    
    static class Notification {
        private Long id;
        private Long userId;
        private String message;
        private NotificationType type;
        private NotificationPriority priority;
        private boolean sent;
        private boolean read;
        private LocalDateTime createdAt;
        private LocalDateTime sentAt;
        private LocalDateTime readAt;
        
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public NotificationType getType() { return type; }
        public void setType(NotificationType type) { this.type = type; }
        public NotificationPriority getPriority() { return priority; }
        public void setPriority(NotificationPriority priority) { this.priority = priority; }
        public boolean isSent() { return sent; }
        public void setSent(boolean sent) { this.sent = sent; }
        public boolean isRead() { return read; }
        public void setRead(boolean read) { this.read = read; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        public LocalDateTime getSentAt() { return sentAt; }
        public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
        public LocalDateTime getReadAt() { return readAt; }
        public void setReadAt(LocalDateTime readAt) { this.readAt = readAt; }
    }
}