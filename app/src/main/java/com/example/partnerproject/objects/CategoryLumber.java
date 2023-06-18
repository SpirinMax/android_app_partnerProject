package com.example.partnerproject.objects;

import java.io.Serializable;
import java.util.Objects;

public class CategoryLumber implements Serializable {
    private int idCategoryLumber;
    private String nameCategory;

    public CategoryLumber() {

    }

    public int getIdCategoryLumber() {
        return idCategoryLumber;
    }

    public void setIdCategoryLumber(int idCategoryLumber) {
        this.idCategoryLumber = idCategoryLumber;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategoryLumber, nameCategory);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CategoryLumber other = (CategoryLumber) obj;
        return idCategoryLumber == other.idCategoryLumber && Objects.equals(nameCategory, other.nameCategory);
    }
}
