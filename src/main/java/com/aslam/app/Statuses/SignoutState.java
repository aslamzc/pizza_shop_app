package com.aslam.app.Statuses;

import com.aslam.app.Interfaces.IProfileState;

public class SignoutState implements IProfileState {
    @Override
    public void display() {
        scanner.success("You have successfully logged out");
    }
}
