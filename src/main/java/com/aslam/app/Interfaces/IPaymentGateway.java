package com.aslam.app.Interfaces;

import com.aslam.app.Entity.Order;

public interface IPaymentGateway {
    String getName();

    void payment(Order order);
}