package org.example.td5.controller;

import org.example.td5.entity.Dish;
import org.example.td5.entity.Ingredient;
import org.example.td5.service.DishService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public List<Dish> getAllDishes() {
        return dishService.getAllDishes();
    }

    @PutMapping("/{id}/ingredients")
    public Dish updateDishIngredients(
            @PathVariable Integer id,
            @RequestBody(required = false) List<Ingredient> ingredients
    ) {
        return dishService.updateDishIngredients(id, ingredients);
    }
}