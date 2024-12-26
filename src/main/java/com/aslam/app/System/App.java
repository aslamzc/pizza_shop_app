package com.aslam.app.System;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.aslam.app.Entity.User;
import com.aslam.app.Entity.Order;
import com.aslam.app.Services.*;
import com.aslam.app.Statuses.OrderDispatchedState;
import com.aslam.app.Statuses.OrderPreparingState;
import com.aslam.app.Utils.SystemScanner;

public class App {

    final private SystemScanner scanner;
    final private SigninService signinService;
    final private UserRegistrationService userRegistrationService;
    final private MyAccountService myAccountService;
    final private FeedbackListService FeedbackListService;
    final private List<User> users = new ArrayList<User>();
    final private List<Order> orders = new ArrayList<Order>();

    final private String[] options = {
            "User Signup",
            "User Signin",
            "Ratings and Feedbacks",
            "Exit"
    };

    public App(SystemScanner scanner) {
        this.scanner = scanner;
        this.userRegistrationService = new UserRegistrationService(scanner, users);
        this.myAccountService = new MyAccountService(scanner, orders);
        this.signinService = new SigninService(myAccountService, scanner, users);
        this.FeedbackListService = new FeedbackListService(scanner, orders);
    }

    public void run() {
        viewInfo();
        User userSeed = new User("aslam", "aslam@pizza.com", "123456", "0777123456");
        userSeed.setLoyaltyPoints(200000);
        users.add(userSeed);
        handleOrderComplete();

        while (true) {
            int option = scanner.questionOption("Please select an option?", "Main Menu", this.options);
            handleService(option);
        }
    }

    private void handleService(int option) {

        scanner.blankSpace();
        switch (option) {
            case 1:
                this.userRegistrationService.handle();
                break;
            case 2:
                this.signinService.handle();
                break;
            case 3:
                this.FeedbackListService.handle();
                break;
            case 4:
                exitSystem();
                break;
            default:
                break;
        }
    }

    private void handleOrderComplete() {

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                NotificationService notifier = new NotificationService();
                orders.forEach(order -> {
                    if (order.getOrderStatus().equalsIgnoreCase(new OrderPreparingState().getState())) {
                        order.setOrderStatus(new OrderDispatchedState());
                        notifier.addToNotification(order, order.getUser());
                    }
                });
                notifier.sendNotification();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 60000);

    }

    private void viewInfo() {
        scanner.blankSpace();
        scanner.heading(" Welcome to the PIZZA SHOP ");
        scanner.highlight("  Order Management System  ");
        scanner.blankSpace();
    }

    private void exitSystem() {
        scanner.success("Thank you for using the PIZZA SHOP - Order Management System!");
        System.exit(0);
    }
}
