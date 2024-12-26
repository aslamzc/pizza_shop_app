package com.aslam.app.PromotionalDiscounts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.aslam.app.Entity.Order;
import com.aslam.app.Services.PromotionService;

public class MothersDayPromotion extends PromotionService {

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
        return 25;
    }

    @Override
    public String getPromotionName() {
        return "Mothers Day Promotion";
    }

    @Override
    public Map<String, LocalDateTime> getPromotionSeason() {
        return new HashMap<>(
                Map.of(
                        "start", LocalDate.parse("2024-05-11").atStartOfDay(),
                        "end", LocalDate.parse("2024-05-11").atStartOfDay()));
    }
}
