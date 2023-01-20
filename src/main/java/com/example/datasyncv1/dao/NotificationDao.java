package com.example.datasyncv1.dao;

import com.example.datasyncv1.interfaces.NotificationRepository;
import com.example.datasyncv1.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;

public class NotificationDao {
    @Autowired
    NotificationRepository notificationRepository;

    public void InsererNotification(Notification n)
    {
        this.notificationRepository.save(n);
    }

}
