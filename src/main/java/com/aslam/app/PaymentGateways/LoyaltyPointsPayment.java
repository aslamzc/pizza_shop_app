package com.aslam.app.PaymentGateways;

import com.aslam.app.Entity.User;
import com.aslam.app.Entity.Order;
import com.aslam.app.Interfaces.IPaymentGateway;
import com.aslam.app.Utils.SystemScanner;

public class LoyaltyPointsPayment implements IPaymentGateway {

    SystemScanner scanner = SystemScanner.getInstance();

    @Override
    public String getName() {
        return "Loyalty Points";
    }

    @Override
    public void payment(Order order) {

        User user = order.getUser();
        if (user.getLoyaltyPoints() < order.getGrandTotal()) {

            scanner.text("You don't have enough loyalty points to pay for this order.");

            if (user.getLoyaltyPoints() > 0) {
                boolean redeem = scanner.questionBoolean(
                        "Would you like to redeem your loyalty points (" + user.getLoyaltyPoints() + ")?");
                if (redeem) {
                    order.redeemLoyaltyPoints(user.getLoyaltyPoints());
                    user.setLoyaltyPoints(0);
                }
            }

        } else {
            user.setLoyaltyPoints(user.getLoyaltyPoints() - order.getFullTotal());
            order.setFullTotal(order.getGrandTotal() - order.getDiscount());
            order.setPaymentGateway(this);
        }

    }

}