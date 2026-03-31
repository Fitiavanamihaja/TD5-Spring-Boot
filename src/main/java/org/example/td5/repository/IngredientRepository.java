package org.example.td5.repository;

import org.example.td5.entity.Ingredient;
import org.example.td5.entity.StockValue;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IngredientRepository {

    private final JdbcTemplate jdbcTemplate;

    public IngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Ingredient> findAll() {
        String sql = "SELECT id, name, category, price FROM ingredient";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Ingredient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price")
                )
        );
    }

    public Ingredient findById(Integer id) {
        String sql = "SELECT id, name, category, price FROM ingredient WHERE id = ?";
        List<Ingredient> results = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) ->
                new Ingredient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price")
                )
        );

        return results.isEmpty() ? null : results.get(0);
    }

    public StockValue getStockByIngredientId(Integer id, String at, String unit) {
        String sql = """
                SELECT COALESCE(SUM(
                    CASE 
                        WHEN type = 'IN' THEN quantity
                        WHEN type = 'OUT' THEN -quantity
                        ELSE 0
                    END
                ), 0) AS stock
                FROM stock_movement
                WHERE id_ingredient = ?
                  AND creation_datetime <= ?::timestamp
                  AND unit = ?::unit
                """;

        List<StockValue> results = jdbcTemplate.query(sql, new Object[]{id, at, unit}, (rs, rowNum) ->
                new StockValue(
                        unit,
                        rs.getDouble("stock")
                )
        );

        return results.isEmpty() ? new StockValue(unit, 0.0) : results.get(0);
    }
}