package com.aslam.app.Entity;

import java.util.*;

import com.aslam.app.Builders.PizzaBuilder;
import com.aslam.app.Enums.QuantityEnum;

abstract class BasePizza {

    protected QuantityEnum pan;
    protected boolean sauce;
    protected List<String> toppings;
    protected List<QuantityEnum> cheese;
    protected QuantityEnum size;

    public BasePizza(PizzaBuilder builder) {
        this.pan = builder.pan;
        this.sauce = builder.sauce;
        this.toppings = builder.toppings;
        this.cheese = builder.cheese;
    }

    public QuantityEnum getPan() {
        return pan;
    }

    public void setPan(QuantityEnum pan) {
        this.pan = pan;
    }

    public void setSauce(boolean sauce) {
        this.sauce = sauce;
    }

    public void addToppings(String topping) {
        this.toppings.add(topping);
    }

    public List<String> getToppings() {
        return this.toppings;
    }

    public void setToppings(List<String> toppings) {
        this.toppings = toppings;
    }

    public void addCheese(QuantityEnum cheese) {
        this.cheese.add(cheese);
    }

    public List<QuantityEnum> getCheese() {
        return this.cheese;
    }

    @Override
    public String toString() {
        return "{" +
                "pan='" + pan + '\'' +
                ", sauce='" + sauce + '\'' +
                ", toppings=" + toppings +
                ", cheese='" + cheese + '\'' +
                '}';
    }

}
