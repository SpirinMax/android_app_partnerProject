package com.example.partnerproject.objects;

import java.io.Serializable;
import java.util.Objects;

public class Lumber implements Serializable {
    private int idLumber;
    private String nameLumber;
    private ParametersLumber parametersLumber;
    private boolean availability;

    public Lumber() {

    }

    public int getIdLumber() {
        return idLumber;
    }

    public void setIdLumber(int idLumber) {
        this.idLumber = idLumber;
    }

    public String getNameLumber() {
        return nameLumber;
    }

    public void setNameLumber(String nameLumber) {
        this.nameLumber = nameLumber;
    }

    public ParametersLumber getParametersLumber() {
        return parametersLumber;
    }

    public void setParametersLumber(ParametersLumber parametersLumber) {
        this.parametersLumber = parametersLumber;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public int hashCode() {
        return Objects.hash(availability, idLumber, nameLumber, parametersLumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Lumber other = (Lumber) obj;
        return availability == other.availability && idLumber == other.idLumber
                && Objects.equals(nameLumber, other.nameLumber)
                && Objects.equals(parametersLumber, other.parametersLumber);
    }
}
