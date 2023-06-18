package com.example.partnerproject.objects;

import java.io.Serializable;
import java.util.Objects;

public class PriceLumber implements Serializable {
    private int idPrice;
    private Lumber lumber;
    private float price;
    private float discountPrice;
    private int amountOrders;
    private CategoryPrice categoryPrice;

    public int getIdPrice() {
        return idPrice;
    }

    public Lumber getLumber() {
        return lumber;
    }

    public void setLumber(Lumber lumber) {
        this.lumber = lumber;
    }

    public void setIdPrice(int idPrice) {
        this.idPrice = idPrice;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(float discountPrice) {
        this.discountPrice = discountPrice;
    }


    public int getAmountOrders() {
        return amountOrders;
    }

    public void setAmountOrders(int amountOrders) {
        this.amountOrders = amountOrders;
    }

    public CategoryPrice getCategoryPrice() {
        return categoryPrice;
    }

    public void setCategoryPrice(CategoryPrice categoryPrice) {
        this.categoryPrice = categoryPrice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountOrders, categoryPrice, discountPrice, idPrice, lumber, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PriceLumber other = (PriceLumber) obj;
        return amountOrders == other.amountOrders && Objects.equals(categoryPrice, other.categoryPrice)
                && Float.floatToIntBits(discountPrice) == Float.floatToIntBits(other.discountPrice)
                && idPrice == other.idPrice && Objects.equals(lumber, other.lumber)
                && Float.floatToIntBits(price) == Float.floatToIntBits(other.price);
    }
}
