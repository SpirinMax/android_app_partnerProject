package com.example.partnerproject.objects;

import java.io.Serializable;
import java.util.Objects;

public class BreedWood implements Serializable {
    private int idBreed;
    private String nameBreed;
    private String varietyBreed;

    public BreedWood() {

    }

    public int getIdBreed() {
        return idBreed;
    }

    public void setIdBreed(int idBreed) {
        this.idBreed = idBreed;
    }

    public String getNameBreed() {
        return nameBreed;
    }

    public void setNameBreed(String nameBreed) {
        this.nameBreed = nameBreed;
    }

    public String getVarietyBreed() {
        return varietyBreed;
    }

    public void setVarietyBreed(String varietyBreed) {
        this.varietyBreed = varietyBreed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBreed, nameBreed, varietyBreed);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BreedWood other = (BreedWood) obj;
        return idBreed == other.idBreed && Objects.equals(nameBreed, other.nameBreed)
                && Objects.equals(varietyBreed, other.varietyBreed);
    }
}
