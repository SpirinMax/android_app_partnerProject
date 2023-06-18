package com.example.partnerproject.objects;

import java.io.Serializable;
public class FilterParameters implements Serializable {
    private float startPrice;

    private float finalPrice;

    private String nameLumber;

    private String categoryPrice = "По м³";

    private int numberPage;

    private String categoryLumber;

    private String nameBreed;

    private int diameter;

    private int width;

    private int lenght;

    private int thickness;

    private boolean availDiscount;

    private boolean sortingByMinPrice;

    private boolean sortingByMaxPrice;

    private boolean sortingByAmountOrders;

    private boolean sortingByDiscountDesc;

    public FilterParameters(boolean sortingByDiscountDesc) {
        this.sortingByDiscountDesc = sortingByDiscountDesc;
    }

    public FilterParameters() {

    }

    public void setStandardCategoryPrice() {
        this.categoryPrice = "По м³";
    }

    public String getParameterSorting() {
        String paramSort = "none";
        if (sortingByMaxPrice) {
            paramSort = "max_price";
        }
        if (sortingByMinPrice) {
            paramSort = "min_price";
        }
        if (sortingByAmountOrders) {
            paramSort = "amount_orders";
        }
        return paramSort;
    }

    public String getNameLumber() {
        return nameLumber;
    }

    public void setNameLumber(String nameLumber) {
        this.nameLumber = nameLumber;
    }

    public String getCategoryPrice() {
        return categoryPrice;
    }

    public void setCategoryPrice(String categoryPrice) {
        this.categoryPrice = categoryPrice;
    }

    public float getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(float startPrice) {
        this.startPrice = startPrice;
    }

    public float getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(float finalPrice) {
        this.finalPrice = finalPrice;
    }

    public int getNumberPage() {
        return numberPage;
    }

    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }

    public boolean isSortingByMinPrice() {
        return sortingByMinPrice;
    }

    public void setSortingByMinPrice(boolean sortingByMinPrice) {
        this.sortingByMinPrice = sortingByMinPrice;
    }

    public boolean isSortingByMaxPrice() {
        return sortingByMaxPrice;
    }

    public void setSortingByMaxPrice(boolean sortingByMaxPrice) {
        this.sortingByMaxPrice = sortingByMaxPrice;
    }

    public boolean isSortingByAmountOrders() {
        return sortingByAmountOrders;
    }

    public void setSortingByAmountOrders(boolean sortingByAmountOrders) {
        this.sortingByAmountOrders = sortingByAmountOrders;
    }

    public String getCategoryLumber() {
        return categoryLumber;
    }

    public void setCategoryLumber(String categoryLumber) {
        this.categoryLumber = categoryLumber;
    }

    public String getNameBreed() {
        return nameBreed;
    }

    public void setNameBreed(String nameBreed) {
        this.nameBreed = nameBreed;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public boolean isAvailDiscount() {
        return availDiscount;
    }

    public void setAvailDiscount(boolean availDiscount) {
        this.availDiscount = availDiscount;
    }

    public boolean isSortingByDiscountDesc() {
        return sortingByDiscountDesc;
    }

    public void setSortingByDiscountDesc(boolean sortingByDiscountDesc) {
        this.sortingByDiscountDesc = sortingByDiscountDesc;
    }

    @Override
    public String toString() {
        return "FilterParameters{" +
                "startPrice=" + startPrice +
                ", finalPrice=" + finalPrice +
                ", nameLumber='" + nameLumber + '\'' +
                ", categoryPrice='" + categoryPrice + '\'' +
                ", numberPage=" + numberPage +
                ", categoryLumber='" + categoryLumber + '\'' +
                ", nameBreed='" + nameBreed + '\'' +
                ", diameter=" + diameter +
                ", width=" + width +
                ", lenght=" + lenght +
                ", thickness=" + thickness +
                ", availDiscount=" + availDiscount +
                ", sortingByMinPrice=" + sortingByMinPrice +
                ", sortingByMaxPrice=" + sortingByMaxPrice +
                ", sortingByAmountOrders=" + sortingByAmountOrders +
                ", sortingByDiscountDesc=" + sortingByDiscountDesc +
                '}';
    }
}
