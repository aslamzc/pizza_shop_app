package com.aslam.app.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.aslam.app.Builders.PizzaBuilder;
import com.aslam.app.Commands.OrderCancelCommand;
import com.aslam.app.Commands.OrderCommand;
import com.aslam.app.Commands.OrderPlaceCommand;
import com.aslam.app.Entity.Pizza;
import com.aslam.app.Entity.User;
import com.aslam.app.Entity.Order;
import com.aslam.app.Enums.QuantityEnum;
import com.aslam.app.Interfaces.IFeedback;
import com.aslam.app.Interfaces.IService;
import com.aslam.app.Utils.SystemScanner;
import com.aslam.app.Utils.FormatNumber;
import com.aslam.app.Utils.InvoiceGenerate;

public class OrderAddService implements IService {

    SystemScanner scanner;
    private User user;
    List<Pizza> pizzaList = new ArrayList<>();
    List<Order> orders;

    public OrderAddService(SystemScanner scanner) {
        this.scanner = scanner;
        this.menuList();
    }

    public OrderAddService(SystemScanner scanner, User user) {
        this.scanner = scanner;
        this.user = user;
        this.menuList();
    }

    @Override
    public void handle() {
        List<Pizza> orderPizza = new ArrayList<>();
        getOrder(orderPizza);
        orderSummary(orderPizza);
        Order order = dispatchOrder(orderPizza);
        viewInvoice(order);
        addFeedback(order);
    }

    public OrderAddService setUser(User user) {
        this.user = user;
        return this;
    }

    public IService setOrders(List<Order> orders) {
        this.orders = orders;
        return this;
    }

    private void addFeedback(Order order) {        
        while (true) {
            int rating = scanner.questionNumber("Rate your experience (1-5): ", true);
            if (rating < 1 || rating > 5) {
                scanner.error("Invalid rating. Please enter a number between 1 and 5.");
                continue;
            }

            String message = scanner.question("Write your feedback: ", true);
            IFeedback feedback = new RatingService(new FeedbackService(message), rating);
            order.addFeedback(feedback);
            scanner.text("Feedback added successfully!");
            break;
        }
    }

    private Order dispatchOrder(List<Pizza> orderPizza) {

        Order order = new Order(user, orderPizza);

        OrderCommand orderCommand = new OrderCommand();
        boolean command = scanner.questionBoolean("Do you want to place the order?");
        if (command) {
            orderCommand.setCommand(new OrderPlaceCommand(order));
        } else {
            orderCommand.setCommand(new OrderCancelCommand(order));
        }
        orderCommand.run();

        order.setOrderDate(LocalDateTime.now());
        orders.add(order);

        return order;
    }

    private void getOrder(List<Pizza> orderPizza) {
        scanner.heading("Create a New Order");

        boolean addMore = true;
        do {
            Pizza selectedPizza = getOrderItem();
            orderPizza.add(selectedPizza);
            if (!scanner.questionBoolean("Do you want to add another pizza?")) {
                addMore = false;
            }

        } while (addMore);
    }

    private void orderSummary(List<Pizza> orderPizza) {
        scanner.blankSpace();
        scanner.highlight(" Order Summary: ");
        orderPizza.forEach(pizza -> {
            scanner.text("-".repeat(50));
            scanner.text((orderPizza.indexOf(pizza) + 1) + ". " + pizza.getName() +
                    " [#" + pizza.getId() + "] (" + pizza.getPan().name() + ") - " +
                    FormatNumber.currency(pizza.getPrice()));
            scanner.text("  - Toppings: " + pizza.getToppings().toString());
            scanner.text("  - Cheese: " + pizza.getCheese().toString());
        });
        scanner.text("-".repeat(50));
        scanner.blankSpace();
    }

    private Pizza getOrderItem() {
        String[] pizzaMenu = pizzaList.stream().map(Pizza::getName).toList().toArray(new String[0]);
        int pizzaItem = scanner.questionOption("What pizza would you like to order: ", "Pizza Menu", pizzaMenu) - 1;

        Pizza selectedPizza = pizzaList.get(pizzaItem);
        scanner.text("You selected the " + selectedPizza.getName() + " pizza.");

        String[] sizes = Arrays.stream(QuantityEnum.values()).map(QuantityEnum::name).toArray(String[]::new);
        int size = scanner.questionOption("Select the pan: ", "What pan would you like?", sizes) - 1;
        selectedPizza.setPan(QuantityEnum.fromCode(size));
        scanner.text("You selected the " + QuantityEnum.fromCode(size) + " Pan.");

        if (scanner.questionBoolean("Would you like to add extra toppings?")) {
            boolean addMore;
            do {
                selectedPizza.addToppings(scanner.question("Add a topping: ", true));
                addMore = scanner.questionBoolean("Add more toppings?:");
            } while (addMore);
        }

        if (scanner.questionBoolean("Would you like to add extra cheese?")) {
            size = scanner.questionOption("Select the portion?", "Cheese portions:", sizes) - 1;
            selectedPizza.addCheese(QuantityEnum.fromCode(size));
        }
        return selectedPizza;
    }

    private void viewInvoice(Order order) {
        InvoiceGenerate.showInvoice(order);
    }

    private void menuList() {
        pizzaList.add(new PizzaBuilder()
                .setName("Sausage Delight")
                .addCheese(QuantityEnum.SMALL)
                .addSauce(true)
                .addTopping("Sausage")
                .addTopping("olive")
                .setPrice(500)
                .build());

        pizzaList.add(new PizzaBuilder()
                .setName("Veggie Supreme")
                .addCheese(QuantityEnum.SMALL)
                .addSauce(true)
                .addTopping("pineapple")
                .addTopping("Onion")
                .setPrice(750)
                .build());

        pizzaList.add(new PizzaBuilder()
                .setName("Tandoori Chicken")
                .addCheese(QuantityEnum.SMALL)
                .addSauce(true)
                .addTopping("Chicken")
                .setPrice(800)
                .build());

        pizzaList.add(new PizzaBuilder()
                .setName("Cheese")
                .addCheese(QuantityEnum.LARGE)
                .addSauce(true)
                .setPrice(500)
                .build());
    }
}
