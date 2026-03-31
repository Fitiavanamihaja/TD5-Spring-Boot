package org.example.td5.TDingredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    Order findOrderByReference(String reference) {
        try (Connection connection = new DBConnection().getConnection()) {

            PreparedStatement ps = connection.prepareStatement("""
                SELECT id, reference, creation_datetime, type, status
                FROM "order"
                WHERE reference = ?
            """);

            ps.setString(1, reference);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Order order = new Order();
                int id = rs.getInt("id");

                order.setId(id);
                order.setReference(rs.getString("reference"));
                order.setCreationDatetime(rs.getTimestamp("creation_datetime").toInstant());
                order.setType(OrderTypeEnum.valueOf(rs.getString("type")));
                order.setStatus(OrderStatusEnum.valueOf(rs.getString("status")));
                order.setDishOrderList(findDishOrderByIdOrder(id));

                return order;
            }

            throw new RuntimeException("Order not found");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Order saveOrder(Order orderToSave) {
        try (Connection conn = new DBConnection().getConnection()) {

            String sql = """
                INSERT INTO "order"(id, reference, creation_datetime, type, status)
                VALUES (?, ?, ?, ?::order_type, ?::order_status)
                ON CONFLICT (id) DO UPDATE
                SET reference = EXCLUDED.reference,
                    type = EXCLUDED.type,
                    status = EXCLUDED.status
                RETURNING id
            """;

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, orderToSave.getId());
            ps.setString(2, orderToSave.getReference());
            ps.setTimestamp(3, Timestamp.from(orderToSave.getCreationDatetime()));
            ps.setString(4, orderToSave.getType().name());
            ps.setString(5, orderToSave.getStatus().name());

            ResultSet rs = ps.executeQuery();
            rs.next();

            orderToSave.setId(rs.getInt(1));
            return orderToSave;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<DishOrder> findDishOrderByIdOrder(Integer idOrder) {
        List<DishOrder> dishOrders = new ArrayList<>();

        try (Connection connection = new DBConnection().getConnection()) {

            PreparedStatement ps = connection.prepareStatement("""
                SELECT id, id_dish, quantity
                FROM dish_order
                WHERE id_order = ?
            """);

            ps.setInt(1, idOrder);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DishOrder dishOrder = new DishOrder();
                dishOrder.setId(rs.getInt("id"));
                dishOrder.setQuantity(rs.getInt("quantity"));

                dishOrders.add(dishOrder);
            }

            return dishOrders;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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