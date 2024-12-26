package com.aslam.app.PaymentGateways;

import com.aslam.app.Entity.Order;
import com.aslam.app.Interfaces.IPaymentGateway;

public class CardPayment implements IPaymentGateway {

    @Override
    public String getName() {
        return "Card Payment";
    }

    @Override
    public void payment(Order order) {
        order.setFullTotal(order.getGrandTotal() - order.getDiscount());
        order.setPaymentGateway(this);
    }
}