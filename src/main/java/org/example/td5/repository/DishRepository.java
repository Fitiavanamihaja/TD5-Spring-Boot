package org.example.td5.repository;

import org.example.td5.entity.Dish;
import org.example.td5.entity.DishIngredient;
import org.example.td5.entity.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DishRepository {

    private final JdbcTemplate jdbcTemplate;

    public DishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Dish> findAll() {
        String sql = "SELECT id, name, selling_price FROM dish";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Integer dishId = rs.getInt("id");
            return new Dish(
                    dishId,
                    rs.getString("name"),
                    rs.getDouble("selling_price"),
                    findIngredientsByDishId(dishId)
            );
        });
    }

    public Dish findById(Integer id) {
        String sql = "SELECT id, name, selling_price FROM dish WHERE id = ?";
        List<Dish> results = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> {
            Integer dishId = rs.getInt("id");
            return new Dish(
                    dishId,
                    rs.getString("name"),
                    rs.getDouble("selling_price"),
                    findIngredientsByDishId(dishId)
            );
        });

        return results.isEmpty() ? null : results.get(0);
    }

    public List<DishIngredient> findIngredientsByDishId(Integer dishId) {
        String sql = """
                SELECT ingredient.id, ingredient.name, ingredient.price, ingredient.category,
                       di.required_quantity, di.unit
                FROM ingredient
                JOIN dish_ingredient di ON di.id_ingredient = ingredient.id
                WHERE di.id_dish = ?
                """;

        return jdbcTemplate.query(sql, new Object[]{dishId}, (rs, rowNum) -> {
            Ingredient ingredient = new Ingredient(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getDouble("price")
            );

            return new DishIngredient(
                    ingredient,
                    rs.getObject("required_quantity") == null ? null : rs.getDouble("required_quantity"),
                    rs.getString("unit")
            );
        });
    }

    public void clearIngredients(Integer dishId) {
        String sql = "DELETE FROM dish_ingredient WHERE id_dish = ?";
        jdbcTemplate.update(sql, dishId);
    }

    public void attachIngredient(Integer dishId, Integer ingredientId) {
        String sql = """
                INSERT INTO dish_ingredient (id_ingredient, id_dish, required_quantity, unit)
                VALUES (?, ?, NULL, 'PCS'::unit)
                """;
        jdbcTemplate.update(sql, ingredientId, dishId);
    }
}