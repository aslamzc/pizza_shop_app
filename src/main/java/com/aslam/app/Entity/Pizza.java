package com.aslam.app.Entity;

import java.util.HashMap;
import java.util.Map;
import com.aslam.app.Builders.PizzaBuilder;
import com.aslam.app.Enums.QuantityEnum;

public class Pizza extends BasePizza
{
    private static int index = 1;
    private final int id;
    protected String name;
    protected double price;
    protected Map<QuantityEnum, Double> priceList = new HashMap<>();

    public Pizza(PizzaBuilder builder) {
        super(builder);
        this.id = index++;
        this.name = builder.getName();
        this.price = builder.getPrice();
        loadPrices();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "{ ID: " + id + " " + super.toString() + " }";
    }

    public double getPrice() {
        return priceList.get(this.getPan());
    }

    protected void loadPrices() {
        priceList.put(QuantityEnum.SMALL, price * 1);
        priceList.put(QuantityEnum.MEDIUM, price * 2);
        priceList.put(QuantityEnum.LARGE, price * 4);
    }
}
