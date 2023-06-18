package com.example.partnerproject.objects;

import java.io.Serializable;
import java.util.Objects;

public class CustomerLogin implements Serializable {
    private int idCustomer;

    private String login;

    private String password;

    public CustomerLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCustomer, login, password);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CustomerLogin other = (CustomerLogin) obj;
        return idCustomer == other.idCustomer && Objects.equals(login, other.login)
                && Objects.equals(password, other.password);
    }
}
