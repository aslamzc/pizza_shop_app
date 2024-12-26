package com.aslam.app.Services;

import java.time.format.DateTimeFormatter;
import java.util.List;
import com.aslam.app.Entity.Order;
import com.aslam.app.Interfaces.IService;
import com.aslam.app.Statuses.OrderDispatchedState;
import com.aslam.app.Utils.SystemScanner;
import com.aslam.app.Utils.InvoiceGenerate;

public class CompletedOrdersService implements IService {

    SystemScanner scanner;
    private List<Order> orders;

    public CompletedOrdersService(SystemScanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void handle() {
        scanner.title("Order History");
        List<Order> pastOrders = orders.stream()
                .filter(order -> order.getOrderStatus().equals(new OrderDispatchedState().getState()))
                .toList();

        if (pastOrders.isEmpty()) {
            scanner.text("No past orders for now.");

        } else {
            viewCompletedOrder(pastOrders);
            if (scanner.questionBoolean("Would you like to view the past order details?")) {
                String orderId = scanner.question("Enter order ID: ", true);
                Order order = orders.stream().filter(o -> o.getId().equals(orderId)).findFirst().orElse(null);
                if (order != null) {
                    InvoiceGenerate.generateInvoice(order);
                }
            }
        }
    }

    public IService setCustomOrders(List<Order> userOrders) {
        this.orders = userOrders;
        return this;
    }

    private void viewCompletedOrder(List<Order> orders) {
        scanner.text("-".repeat(82));
        System.out.printf("%-6s | %-18s | %-16s | %-18s | %-14s%n",
                "ID",
                "Date",
                "Status",
                "Payment",
                "Total");
        scanner.text("-".repeat(82));
        orders.forEach(order -> System.out.printf("%-6s | %-18s | %-16s | %-18s | %-14s%n",
                order.getId(),
                order.getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                order.getOrderStatus(),
                order.getPaymentGateway().getName(),
                order.getFullTotal()));
        scanner.blankSpace();
    }
}
