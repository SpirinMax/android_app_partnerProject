package com.example.partnerproject.objects;

import java.io.Serializable;
import java.util.Objects;

public class CategoryPrice implements Serializable {
    private int idCategoryPrice;
    private String nameCategoryPrice;
    private boolean discount;
    private float discountAmount;

    public CategoryPrice() {
    }

    public int getIdCategoryPrice() {
        return idCategoryPrice;
    }

    public void setIdCategoryPrice(int idCategoryPrice) {
        this.idCategoryPrice = idCategoryPrice;
    }

    public String getNameCategoryPrice() {
        return nameCategoryPrice;
    }

    public void setNameCategoryPrice(String nameCategoryPrice) {
        this.nameCategoryPrice = nameCategoryPrice;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(float discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategoryPrice, discount, discountAmount, nameCategoryPrice);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CategoryPrice other = (CategoryPrice) obj;
        return idCategoryPrice == other.idCategoryPrice && discount == other.discount
                && Float.floatToIntBits(discountAmount) == Float.floatToIntBits(other.discountAmount)
                && Objects.equals(nameCategoryPrice, other.nameCategoryPrice);
    }
}
