package com.aslam.app.Entity;

import com.aslam.app.Interfaces.IOrderObserver;
import com.aslam.app.Interfaces.IProfileState;
import com.aslam.app.Statuses.NewUserState;
import com.aslam.app.Utils.SystemScanner;

public class User implements IOrderObserver {

    private String name;
    private String email;
    private String password;
    private String phone;
    private IProfileState profileState;
    private SystemScanner scanner;
    private double loyaltyPoints = 0;

    public User(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.profileState = new NewUserState();
        this.scanner = SystemScanner.getInstance();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public boolean isPasswordMatch(String password) {
        return this.password.equals(password);
    }

    public IProfileState getState() {
        return profileState;
    }

    public void setState(IProfileState state) {
        this.profileState = state;
    }

    public double getLoyaltyPoints() {
        return this.loyaltyPoints;
    }

    public double addLoyaltyPoints(double amount) {
        return this.loyaltyPoints += amount;
    }

    public void setLoyaltyPoints(double loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    @Override
    public void notify(String status) {
        scanner.success("Order Status: " + status);
    }
}
