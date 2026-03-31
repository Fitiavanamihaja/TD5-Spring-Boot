package org.example.td5.service;

import org.example.td5.dto.CreateDishRequest;
import org.example.td5.entity.Dish;
import org.example.td5.entity.Ingredient;
import org.example.td5.exception.BadRequestException;
import org.example.td5.repository.DishRepository;
import org.example.td5.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final IngredientRepository ingredientRepository;

    public DishService(DishRepository dishRepository, IngredientRepository ingredientRepository) {
        this.dishRepository = dishRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<Dish> getAllDishes(Double priceUnder, Double priceOver, String name) {
        return dishRepository.findAll(priceUnder, priceOver, name);
    }

    public Dish updateDishIngredients(Integer dishId, List<Ingredient> ingredients) {
        Dish dish = dishRepository.findById(dishId);
        if (dish == null) {
            throw new RuntimeException("Dish.id=" + dishId + " is not found");
        }

        if (ingredients == null || ingredients.isEmpty()) {
            throw new BadRequestException("Request body is required and must contain a list of ingredients.");
        }

        dishRepository.clearIngredients(dishId);

        for (Ingredient ingredient : ingredients) {
            Ingredient existingIngredient = ingredientRepository.findById(ingredient.getId());
            if (existingIngredient != null) {
                dishRepository.attachIngredient(dishId, existingIngredient.getId());
            }
        }

        return dishRepository.findById(dishId);
    }

    public List<Dish> createDishes(List<CreateDishRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            throw new BadRequestException("Request body is required and must contain a list of dishes.");
        }

        List<Dish> createdDishes = new ArrayList<>();

        for (CreateDishRequest request : requests) {
            if (request.getName() == null || request.getName().isBlank()) {
                throw new BadRequestException("Dish.name is required");
            }

            if (dishRepository.existsByName(request.getName())) {
                throw new BadRequestException("Dish.name=" + request.getName() + " already exists");
            }

            createdDishes.add(dishRepository.save(request));
        }

        return createdDishes;
    }
}