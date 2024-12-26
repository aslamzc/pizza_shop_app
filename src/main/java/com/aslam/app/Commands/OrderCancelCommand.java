package com.aslam.app.Commands;

import com.aslam.app.Entity.Order;
import com.aslam.app.Interfaces.ICommand;

public class OrderCancelCommand implements ICommand {

    public OrderCancelCommand(Order order) {
    }

    @Override
    public void handle() {
        System.out.println("Order has been canceled.");
    }
}
