package com.aslam.app.Services;

import java.util.List;
import com.aslam.app.Entity.User;
import com.aslam.app.Entity.Order;
import com.aslam.app.Interfaces.IService;
import com.aslam.app.Utils.SystemScanner;

public class MyAccountService implements IService {

    private SystemScanner scanner;
    private final List<Order> orders;
    private SigninService signinService;
    private ProfileService profileService;
    private OrderAddService orderAddService;
    private OrderProcessingService orderProcessingService;
    private CompletedOrdersService completedOrdersService;

    final private String[] options = {
            "Add New Order",
            "View Processing Order List",
            "View Completed Orders",
            "Edit Profile",
            "Signout"
    };

    public MyAccountService(SystemScanner scanner, List<Order> orders) {
        this.scanner = scanner;
        this.orders = orders;
        this.orderAddService = new OrderAddService(scanner);
        this.orderProcessingService = new OrderProcessingService(scanner);
        this.completedOrdersService = new CompletedOrdersService(scanner);
        this.profileService = new ProfileService(scanner);
    }

    public IService setUserService(SigninService userSigninService) {
        this.signinService = userSigninService;
        return this;
    }

    @Override
    public void handle() {
        scanner.blankSpace();
        User user = signinService.getLoggedInUser();
        while (true) {
            List<Order> usersOrders = orders.stream()
                    .filter(order -> order.getUser().equals(user))
                    .toList();
            int option = scanner.questionOption(
                    "Choose an option: ",
                    "What would you like to do?",
                    options);
            switch (option) {
                case 1:
                    this.orderAddService.setUser(user).setOrders(orders).handle();
                    break;
                case 2:
                    orderProcessingService.setOrders(usersOrders).handle();
                    break;
                case 3:
                    completedOrdersService.setCustomOrders(usersOrders).handle();
                    break;
                case 4:
                    profileService.setUser(user).handle();
                    break;
                case 5:
                    signinService.logout();
                    scanner.success("You have successfully logged out!");
                    return;
            }
        }
    }
}
