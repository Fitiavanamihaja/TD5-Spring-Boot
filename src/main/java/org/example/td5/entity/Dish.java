package org.example.td5.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Dish {
    private Integer id;
    private String name;
    private String dishType;

    @JsonProperty("sellingPrice")
    private Double sellingPrice;

    private List<DishIngredient> ingredients;

    public Dish() {
    }

    public Dish(Integer id, String name, String dishType, Double sellingPrice, List<DishIngredient> ingredients) {
        this.id = id;
        this.name = name;
        this.dishType = dishType;
        this.sellingPrice = sellingPrice;
        this.ingredients = ingredients;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDishType() {
        return dishType;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public List<DishIngredient> getIngredients() {
        return ingredients;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setIngredients(List<DishIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}