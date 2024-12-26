package com.aslam.app.Statuses;

import com.aslam.app.Interfaces.IOrderState;

public class OrderDispatchedState implements IOrderState {

    @Override
    public String getState() {
        return "Order Completed";
    }
}
