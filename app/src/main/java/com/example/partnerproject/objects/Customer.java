package com.example.partnerproject.objects;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Customer implements Serializable {
    private int idCustomer;
    private String phone;
    private String FIO;
    private String address;
    private boolean legalCompany;
    private String nameCompany;
    private String addressCompany;
    private Set<OrderLumber> ordersLumbers = new HashSet<>();

    public Customer() {

    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String fIO) {
        FIO = fIO;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isLegalCompany() {
        return legalCompany;
    }

    public void setLegalCompany(boolean legalCompany) {
        this.legalCompany = legalCompany;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getAddressCompany() {
        return addressCompany;
    }

    public void setAddressCompany(String addressCompany) {
        this.addressCompany = addressCompany;
    }

    public Set<OrderLumber> getOrdersLumbers() {
        return ordersLumbers;
    }

    public void setOrdersLumbers(Set<OrderLumber> ordersLumbers) {
        this.ordersLumbers = ordersLumbers;
    }

    @Override
    public int hashCode() {
        return Objects.hash(FIO, address, addressCompany, idCustomer, legalCompany, nameCompany, phone);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
        return Objects.equals(FIO, other.FIO) && Objects.equals(address, other.address)
                && Objects.equals(addressCompany, other.addressCompany) && idCustomer == other.idCustomer
                && legalCompany == other.legalCompany && Objects.equals(nameCompany, other.nameCompany)
                && Objects.equals(phone, other.phone);
    }
}
