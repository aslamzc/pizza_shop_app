package com.aslam.app.Services;

import java.util.HashMap;
import java.util.Map;
import com.aslam.app.Entity.Order;
import com.aslam.app.Interfaces.IOrderObserver;

public class NotificationService {

    private final Map<Order, IOrderObserver> observers = new HashMap<>();

    public void sendNotification() {
        observers.forEach((order, observer) -> observer.notify(order.getOrderStatus()));
    }

    public void addToNotification(Order order, IOrderObserver observer) {
        observers.put(order, observer);
    }
}