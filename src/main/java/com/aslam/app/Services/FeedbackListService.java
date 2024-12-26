package com.aslam.app.Services;

import java.util.List;
import com.aslam.app.Entity.Order;
import com.aslam.app.Interfaces.IService;
import com.aslam.app.Utils.SystemScanner;

public class FeedbackListService implements IService {

    final private SystemScanner scanner;
    final private List<Order> orders;

    public FeedbackListService(SystemScanner scanner, List<Order> orders) {
        this.scanner = scanner;
        this.orders = orders;
    }

    @Override
    public void handle() {
        scanner.title("Pizza Shop Ratings and Feedbacks");
        orders.forEach(order -> {
            scanner.text(order.getUser().getName() + " added feedback for order #" + order.getId());
            scanner.text("Feedback: " + order.getFeedback().getFeedback());
            scanner.text("Rating: " + order.getFeedback().getRating());
            scanner.text("-".repeat(56));
        });
    }
}
