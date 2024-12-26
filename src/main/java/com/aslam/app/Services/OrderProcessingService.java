package com.aslam.app.Services;

import java.time.format.DateTimeFormatter;
import java.util.List;
import com.aslam.app.Entity.Order;
import com.aslam.app.Interfaces.IService;
import com.aslam.app.Statuses.OrderPreparingState;
import com.aslam.app.Utils.SystemScanner;
import com.aslam.app.Utils.InvoiceGenerate;

public class OrderProcessingService implements IService {

    SystemScanner scanner;
    private List<Order> orders;

    public OrderProcessingService(SystemScanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void handle() {
        scanner.title("Processing Orders");
        List<Order> ongoingOrders = orders.stream()
                .filter(order -> order.getOrderStatus().equals(new OrderPreparingState().getState()))
                .toList();

        if (ongoingOrders.isEmpty()) {
            scanner.text("No Processing Orders.");

        } else {
            showProcessingOrders(ongoingOrders);
            if (scanner.questionBoolean("Would you like to view details?")) {
                String orderId = scanner.question("Enter order ID: ", true);
                Order order = orders.stream().filter(o -> o.getId().equals(orderId)).findFirst().orElse(null);
                if (order != null) {
                    InvoiceGenerate.generateInvoice(order);
                }
            }
        }

    }

    private void showProcessingOrders(List<Order> orders) {
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

    public IService setOrders(List<Order> orders) {
        this.orders = orders;
        return this;
    }
}
