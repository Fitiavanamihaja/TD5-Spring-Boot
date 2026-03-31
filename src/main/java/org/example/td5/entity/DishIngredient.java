package org.example.td5.entity;

public class DishIngredient {
    private Ingredient ingredient;
    private Double requiredQuantity;
    private String unit;

    public DishIngredient() {
    }

    public DishIngredient(Ingredient ingredient, Double requiredQuantity, String unit) {
        this.ingredient = ingredient;
        this.requiredQuantity = requiredQuantity;
        this.unit = unit;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Double getRequiredQuantity() {
        return requiredQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setRequiredQuantity(Double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}