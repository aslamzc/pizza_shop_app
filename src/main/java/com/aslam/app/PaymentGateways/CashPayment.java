package com.aslam.app.PaymentGateways;

import com.aslam.app.Entity.Order;
import com.aslam.app.Interfaces.IPaymentGateway;

public class CashPayment implements IPaymentGateway {
    @Override
    public String getName() {
        return "Cash Payment";
    }

    @Override
    public void payment(Order order) {
        order.setFullTotal(order.getGrandTotal() - order.getDiscount());
        order.setPaymentGateway(this);
    }
}
