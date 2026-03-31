package org.example.td5.Ingredientk3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dish {
    private Integer id;
    private String name;
    private Double price;
    private DishTypeEnum dishType;
    private List<DishIngredient> dishIngredients;


    public Dish() {
        this.dishIngredients = new ArrayList<>();
    }


    public Dish(String name, Double price, DishTypeEnum dishType) {
        this.name = name;
        this.price = price;
        this.dishType = dishType;
        this.dishIngredients = new ArrayList<>();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public DishTypeEnum getDishType() {
        return dishType;
    }

    public void setDishType(DishTypeEnum dishType) {
        this.dishType = dishType;
    }

    public List<DishIngredient> getDishIngredients() {
        return dishIngredients;
    }

    public void setDishIngredients(List<DishIngredient> dishIngredients) {
        if (dishIngredients == null) {
            this.dishIngredients = new ArrayList<>();
            return;
        }
        for (DishIngredient ingredient : dishIngredients) {
            ingredient.setDish(this); // Associe l'ingrédient au plat
        }
        this.dishIngredients = dishIngredients;
    }


    public Double getDishCost() {
        double totalCost = 0.0;
        for (DishIngredient di : dishIngredients) {
            Double quantity = di.getQuantity();
            if (quantity == null) {
                throw new RuntimeException("Certaines quantités d'ingrédients ne sont pas définies");
            }
            totalCost += di.getIngredient().getPrice() * quantity;
        }
        return totalCost;
    }


    public Double getGrossMargin() {
        if (price == null) {
            throw new RuntimeException("Le prix est nul");
        }
        return price - getDishCost();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Objects.equals(id, dish.id) &&
                Objects.equals(name, dish.name) &&
                dishType == dish.dishType &&
                Objects.equals(dishIngredients, dish.dishIngredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dishType, dishIngredients);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type=" + dishType +
                ", cost=" + getDishCost() +
                ", grossMargin=" + getGrossMargin() +
                ", ingredients=" + dishIngredients +
                '}';
    }
}