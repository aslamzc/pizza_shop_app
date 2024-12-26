package com.aslam.app.Statuses;

import com.aslam.app.Interfaces.IProfileState;

public class NewUserState implements IProfileState {
    @Override
    public void display() {
        scanner.success("Thank you! You have successfully registered");
    }
}