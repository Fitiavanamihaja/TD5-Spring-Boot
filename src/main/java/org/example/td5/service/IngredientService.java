package org.example.td5.service;

import org.example.td5.entity.Ingredient;
import org.example.td5.entity.StockValue;
import org.example.td5.exception.BadRequestException;
import org.example.td5.exception.NotFoundException;
import org.example.td5.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientById(Integer id) {
        Ingredient ingredient = ingredientRepository.findById(id);
        if (ingredient == null) {
            throw new NotFoundException("Ingredient.id=" + id + " is not found");
        }
        return ingredient;
    }

    public StockValue getIngredientStock(Integer id, String at, String unit) {
        if (at == null || unit == null || at.isBlank() || unit.isBlank()) {
            throw new BadRequestException("Either mandatory query parameter `at` or `unit` is not provided.");
        }

        Ingredient ingredient = ingredientRepository.findById(id);
        if (ingredient == null) {
            throw new NotFoundException("Ingredient.id=" + id + " is not found");
        }

        return ingredientRepository.getStockByIngredientId(id, at, unit);
    }
}