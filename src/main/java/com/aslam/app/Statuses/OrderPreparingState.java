package com.aslam.app.Statuses;

import com.aslam.app.Interfaces.IOrderState;

public class OrderPreparingState implements IOrderState {

    @Override
    public String getState() {
        return "Order Preparing";
    }
}
