package com.example.partnerproject.objects;

import java.io.Serializable;
import java.util.Objects;

public class ParametersLumber implements Serializable {
    private int idParamemtersLumber;
    private CategoryLumber categoryLumber;
    private BreedWood breedWood;
    private String varietyLumber;
    private int diameter;
    private int width;
    private int lenght;
    private int thickness;
    private byte[] imageLumber;

    public ParametersLumber() {

    }

    public int getIdParamemtersLumber() {
        return idParamemtersLumber;
    }

    public void setIdParamemtersLumber(int idParamemtersLumber) {
        this.idParamemtersLumber = idParamemtersLumber;
    }

    public CategoryLumber getCategoryLumber() {
        return categoryLumber;
    }

    public void setCategoryLumber(CategoryLumber categoryLumber) {
        this.categoryLumber = categoryLumber;
    }

    public BreedWood getBreedWood() {
        return breedWood;
    }

    public void setBreedWood(BreedWood breedWood) {
        this.breedWood = breedWood;
    }

    public String getVarietyLumber() {
        return varietyLumber;
    }

    public void setVarietyLumber(String varietyLumber) {
        this.varietyLumber = varietyLumber;
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

    public byte[] getImageLumber() {
        return imageLumber;
    }

    public void setImageLumber(byte[] imageLumber) {
        this.imageLumber = imageLumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idParamemtersLumber, diameter, lenght, thickness, varietyLumber, width, breedWood,
                categoryLumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ParametersLumber other = (ParametersLumber) obj;
        return idParamemtersLumber == other.idParamemtersLumber && diameter == other.diameter && lenght == other.lenght
                && thickness == other.thickness && Objects.equals(varietyLumber, other.varietyLumber)
                && width == other.width && Objects.equals(breedWood, other.breedWood)
                && Objects.equals(categoryLumber, other.categoryLumber);
    }
}
