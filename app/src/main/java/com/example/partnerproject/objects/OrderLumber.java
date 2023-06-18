package com.example.partnerproject.objects;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class OrderLumber implements Serializable {
    private int idOrder;
    private int idCustomer;
    private PriceLumber priceLumber;
    private double quantity;
    private double finalPrice;
    private Date dateOrder;

    public OrderLumber() {

    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public PriceLumber getPriceLumber() {
        return priceLumber;
    }

    public void setPriceLumber(PriceLumber priceLumber) {
        this.priceLumber = priceLumber;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateOrder, finalPrice, idCustomer, idOrder, quantity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderLumber other = (OrderLumber) obj;
        return Objects.equals(dateOrder, other.dateOrder)
                && Double.doubleToLongBits(finalPrice) == Double.doubleToLongBits(other.finalPrice)
                && idCustomer == other.idCustomer && idOrder == other.idOrder
                && Double.doubleToLongBits(quantity) == Double.doubleToLongBits(other.quantity);
    }
}
