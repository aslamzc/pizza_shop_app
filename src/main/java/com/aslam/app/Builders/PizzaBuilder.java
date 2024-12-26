package com.aslam.app.Builders;

import java.util.*;
import com.aslam.app.Entity.Pizza;
import com.aslam.app.Enums.QuantityEnum;

public class PizzaBuilder {

    private String name;
    private double price;
    public QuantityEnum pan;
    public boolean sauce;
    public List<String> toppings = new ArrayList<>();
    public List<QuantityEnum> cheese = new ArrayList<>();

    public PizzaBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public PizzaBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public PizzaBuilder addCheese(QuantityEnum cheese) {
        this.cheese.add(cheese);
        return this;
    }

    public PizzaBuilder addTopping(String topping) {
        this.toppings.add(topping);
        return this;
    }

    public PizzaBuilder addSauce(boolean sauce) {
        this.sauce = sauce;
        return this;
    }

    public Pizza build() {
        return new Pizza(this);
    }
}