package com.aslam.app.Entity;

import java.time.LocalDateTime;
import java.util.List;

import com.aslam.app.Interfaces.IFeedback;
import com.aslam.app.Interfaces.IOrderState;
import com.aslam.app.Interfaces.IPaymentGateway;
import com.aslam.app.Services.PromotionService;
import com.aslam.app.Services.RatingService;

public class Order {

    private static int index = 1;
    private int id;
    private User user;
    private List<Pizza> pizzas;
    private double total;
    private double discount;
    private double redeemPoints = 0;
    private PromotionService promotion;
    private IPaymentGateway paymentGateway;
    private IOrderState orderStatus;
    private IFeedback feedback;
    private LocalDateTime orderDate;

    public Order(User user, List<Pizza> pizzas) {
        this.user = user;
        this.pizzas = pizzas;
        this.id = index++;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public void addPizzas(Pizza pizza) {
        this.pizzas.add(pizza);
    }

    public List<Pizza> getOrderItems() {
        return pizzas;
    }

    public User getUser() {
        return user;
    }

    public double getGrandTotal() {
        double total = 0;
        for (Pizza pizza : pizzas) {
            total += pizza.getPrice();
        }
        return total;
    }

    public double getDiscount() {
        return discount;
    }

    public double getFullTotal() {
        return total;
    }

    public IPaymentGateway getPaymentGateway() {
        return paymentGateway;
    }

    public void addPromotion(PromotionService promotion) {
        this.promotion = promotion;
    }

    public void setDiscount(double calculateDiscount) {
        this.discount = calculateDiscount;
    }

    public void setFullTotal(double newTotal) {
        this.total = newTotal;
    }

    public void setPaymentGateway(IPaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void setOrderStatus(IOrderState orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus.getState();
    }

    public void addFeedback(IFeedback feedback) {
        this.feedback = feedback;
    }

    public void redeemLoyaltyPoints(double loyaltyPoints) {
        redeemPoints = loyaltyPoints;
    }

    public void setOrderDate(LocalDateTime now) {
        this.orderDate = now;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public RatingService getFeedback() {
        return (RatingService) feedback;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", pizzas=" + pizzas +
                ", total=" + total +
                ", discount=" + discount +
                ", redeemPoints=" + redeemPoints +
                ", promotion=" + promotion.getPromotionName() +
                ", paymentGateway=" + paymentGateway.getClass().getSimpleName() +
                '}';
    }

    public PromotionService getPromotion() {
        return promotion;
    }

    public double getRedeemedPoints() {
        return redeemPoints;
    }
}
