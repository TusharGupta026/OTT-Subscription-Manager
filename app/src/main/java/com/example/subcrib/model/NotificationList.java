package com.example.subcrib.model;

public class NotificationList {
    private int notificationId;
    private String notification;


    public NotificationList(int notificationId, String notification) {
        this.notificationId = notificationId;
        this.notification = notification;
    }


    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

}

