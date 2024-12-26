package com.aslam.app.Utils;

import java.time.format.DateTimeFormatter;
import com.aslam.app.Entity.Order;
import com.aslam.app.Enums.ThemeEnum;

public class InvoiceGenerate {

    private static SystemScanner scanner = SystemScanner.getInstance();

    public static void showInvoice(Order order) {

        if (!scanner.questionBoolean("Do you want to print the invoice?"))
            return;
        generateInvoice(order);
        scanner.text("Thank you for your order!");
        scanner.text("You earned " + (order.getGrandTotal() * 0.01) + " points for this order.");

    }

    public static void generateInvoice(Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String title = "      INVOICE      ";
        String separator = "-".repeat(title.length() + 22);

        scanner.blankSpace();
        scanner.title(title);
        scanner.text("Invoice ID: #" + order.getId());
        scanner.text("Invoice Date: " + order.getOrderDate().format(formatter));
        scanner.text(separator);
        scanner.text(ThemeEnum.WHITE_UNDERLINED + "Items");
        final int[] itemCount = { 0 };
        order.getOrderItems().forEach(pizza -> {
            itemCount[0]++;
            System.out.printf("%-44s %-12s%n",
                    itemCount[0] + ". " + pizza.getName() +
                            " [#" + pizza.getId() + "] (" + pizza.getPan().name() + ")",
                    FormatNumber.currency(pizza.getPrice()));
            scanner.text("  - Toppings: " + pizza.getToppings().toString());
            scanner.text("  - Cheese: " + pizza.getCheese().toString());
        });
        scanner.text(separator);

        System.out.printf("%-44s %-12s%n", "Gross Total: ", FormatNumber.currency(order.getGrandTotal()));
        System.out.printf("%-44s %-12s%n",
                "Discount (" + order.getPromotion().getPromotionName() + " - "
                        + order.getPromotion().getDiscount() + "%): ",
                FormatNumber.currency(order.getDiscount()));
        if (order.getRedeemedPoints() > 0) {
            System.out.printf("%-44s %-12s%n", "Loyalty Points Redeemed: ",
                    FormatNumber.currency(order.getRedeemedPoints()));
            System.out.printf("%-44s %-12s%n", "Net Total: ",
                    FormatNumber.currency(order.getFullTotal() - order.getRedeemedPoints()));
        } else {
            System.out.printf("%-44s %-12s%n", "Net Total: ", FormatNumber.currency(order.getFullTotal()));
        }
        scanner.text(separator);
    }
}
