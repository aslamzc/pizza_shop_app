package com.aslam.app.Commands;

import com.aslam.app.Entity.Order;
import com.aslam.app.Interfaces.ICommand;
import com.aslam.app.PaymentGateways.CashPayment;
import com.aslam.app.PaymentGateways.CardPayment;
import com.aslam.app.PaymentGateways.LoyaltyPointsPayment;
import com.aslam.app.PromotionalDiscounts.FestivalPromotion;
import com.aslam.app.PromotionalDiscounts.MothersDayPromotion;
import com.aslam.app.Services.NotificationService;
import com.aslam.app.Services.PaymentGatewayService;
import com.aslam.app.Services.PromotionService;
import com.aslam.app.Statuses.OrderPreparingState;
import com.aslam.app.Utils.SystemScanner;

public class OrderPlaceCommand implements ICommand {

    private final Order order;
    private final SystemScanner scanner;

    public OrderPlaceCommand(Order order) {
        this.order = order;
        this.scanner = SystemScanner.getInstance();
    }

    @Override
    public void handle() {      
        orderPromotionAndDiscount();       
        orderPayment();
        order.setOrderStatus(new OrderPreparingState());
        order.getUser().addLoyaltyPoints(order.getGrandTotal() * 0.01);
        scanner.success("Order has been placed.");
        NotificationService notifier = new NotificationService();
        notifier.addToNotification(order, order.getUser());
        notifier.sendNotification();
    }

    private void orderPromotionAndDiscount() {
        PromotionService festival = new FestivalPromotion();
        PromotionService mothersDay = new MothersDayPromotion();
        festival.handleNextPromotion(mothersDay);
        festival.addDiscount(order);
    }

    private void orderPayment() {
        PaymentGatewayService paymentGateway = new PaymentGatewayService();
        do {
            String[] gateways = { "Cash Payment", "Credit Card", "Loyalty Points" };
            int paymentMethod = scanner.questionOption("Select option:", "Choose your preferred payment method? ",
                    gateways);

            switch (paymentMethod) {
                case 1:
                    paymentGateway.setPaymentGateway(new CashPayment());
                    break;
                case 2:
                    paymentGateway.setPaymentGateway(new CardPayment());
                    break;
                case 3:
                    paymentGateway.setPaymentGateway(new LoyaltyPointsPayment());
                    break;
            }
            paymentGateway.payment(order);

        } while (order.getPaymentGateway() == null);
    }

    
}
