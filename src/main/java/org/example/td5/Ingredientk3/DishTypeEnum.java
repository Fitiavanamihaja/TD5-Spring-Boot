package org.example.td5.Ingredientk3;

public enum DishTypeEnum {
    STARTER("Entrée"),
    MAIN("Plat principal"),
    DESSERT("Dessert"),
    DRINK("Boisson");

    private final String label;

    DishTypeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}