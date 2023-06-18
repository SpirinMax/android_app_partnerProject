package com.example.partnerproject.utils;

import com.example.partnerproject.objects.FilterParameters;
import com.example.partnerproject.objects.PriceLumber;

public class ClassesForPutExtra {
    private FilterParameters filter;
    private PriceLumber price;

    public ClassesForPutExtra(FilterParameters filter, PriceLumber price) {
        this.filter = filter;
        this.price = price;
    }

    public ClassesForPutExtra() {

    }

    public FilterParameters getFilter() {
        return filter;
    }

    public void setFilter(FilterParameters filter) {
        this.filter = filter;
    }

    public PriceLumber getPrice() {
        return price;
    }

    public void setPrice(PriceLumber price) {
        this.price = price;
    }
}
