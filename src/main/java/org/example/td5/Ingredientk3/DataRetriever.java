package org.example.td5.Ingredientk3;
import org.example.td5.TDingredient.DishTypeEnum;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    Dish findDishById(Integer id) {
        try (Connection connection = new DBConnection().getConnection()) {

            PreparedStatement ps = connection.prepareStatement("""
                SELECT id, name, dish_type, selling_price
                FROM dish
                WHERE id = ?
            """);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Dish dish = new Dish();

                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setDishType(DishTypeEnum.valueOf(rs.getString("dish_type")));
                dish.setPrice(rs.getDouble("selling_price"));

                return dish;
            }

            throw new RuntimeException("Dish not found " + id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}