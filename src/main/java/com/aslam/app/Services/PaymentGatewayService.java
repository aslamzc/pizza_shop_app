package com.aslam.app.Services;

import com.aslam.app.Entity.Order;
import com.aslam.app.Interfaces.IPaymentGateway;

public class PaymentGatewayService {

    private IPaymentGateway paymentGateway;

    public void payment(Order order) {
        paymentGateway.payment(order);
    }

    public void setPaymentGateway(IPaymentGateway payment) {
        this.paymentGateway = payment;
    }
}
