package com.aslam.app.PromotionalDiscounts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.aslam.app.Entity.Order;
import com.aslam.app.Services.PromotionService;

public class FestivalPromotion extends PromotionService {

    @Override
    public void addDiscount(Order order) {

        Map<String, LocalDateTime> dates = getPromotionSeason();

        if (today.isAfter(dates.get("start")) && today.isBefore(dates.get("end"))) {
            updateOrder(order);

        } else if (nextHandler != null) {
            nextHandler.addDiscount(order);
        }
    }

    @Override
    public double getDiscount() {
        return 15;
    }

    @Override
    public String getPromotionName() {
        return "Festival Promotion";
    }

    @Override
    public Map<String, LocalDateTime> getPromotionSeason() {
        return new HashMap<>(
                Map.of(
                        "start", LocalDate.parse("2024-12-01").atStartOfDay(),
                        "end", LocalDate.parse("2024-12-31").atStartOfDay()));
    }
}
