package com.aslam.app.Services;

import java.time.LocalDateTime;
import java.util.Map;
import com.aslam.app.Entity.Order;

public abstract class PromotionService {

    protected PromotionService nextHandler;
    protected LocalDateTime today = LocalDateTime.now();

    public void handleNextPromotion(PromotionService nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void addDiscount(Order order);

    public abstract double getDiscount();

    public abstract String getPromotionName();

    public abstract Map<String, LocalDateTime> getPromotionSeason();

    public void updateOrder(Order order) {
        double orderTotal = order.getGrandTotal();
        double calculateDiscount = (orderTotal * getDiscount()) / 100;
        double newTotal = orderTotal - calculateDiscount;
        order.setDiscount(calculateDiscount);
        order.setFullTotal(newTotal);
        order.addPromotion(this);
    }
}
