package com.food.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.food.model.OrderItem;
import com.food.utility.UtilityClass;

public class OrderItemDAO {

	
	    private static final String INSERT_ORDER_ITEM_SQL =
	        "INSERT INTO orderitem (orderid, menuid, quantity, price) VALUES (?, ?, ?, ?)";

	    /**
	     * Adds a new order item record to the orderitem table.
	     * 
	     * @param item - OrderItem object containing details
	     * @param conn - Database connection object
	     * @throws SQLException - If any SQL exception occurs
	     */
	    public void addOrderItem(OrderItem item, Connection conn) throws SQLException {
	        try (PreparedStatement pstmt = conn.prepareStatement(INSERT_ORDER_ITEM_SQL)) {
	            pstmt.setInt(1, item.getOrderId());
	            pstmt.setInt(2, item.getMenuId());
	            pstmt.setInt(3, item.getQuantity());
	            pstmt.setDouble(4, item.getPrice());
	            pstmt.executeUpdate();
	        }
	    }


    public OrderItem getOrderItem(int orderItemId) {
        OrderItem orderItem = null;
        try (Connection connection = UtilityClass.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(
                     "SELECT * FROM orderitem WHERE orderItemId=?")) {

            prepareStatement.setInt(1, orderItemId);
            ResultSet res = prepareStatement.executeQuery();

            if (res.next()) {
                orderItem = new OrderItem(
                        res.getInt("orderItemId"),
                        res.getInt("orderId"),
                        res.getInt("menuId"),
                        res.getInt("quantity"),
                        res.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItem;
    }

    public List<OrderItem> getAll() {
        List<OrderItem> orderItemsList = new ArrayList<>();
        try (Connection connection = UtilityClass.getConnection();
             Statement createStatement = connection.createStatement();
             ResultSet res = createStatement.executeQuery("SELECT * FROM orderitem")) {

            while (res.next()) {
                orderItemsList.add(new OrderItem(
                        res.getInt("orderItemId"),
                        res.getInt("orderId"),
                        res.getInt("menuId"),
                        res.getInt("quantity"),
                        res.getDouble("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItemsList;
    }

    public void updateOrderItem(OrderItem orderItem) {
        try (Connection connection = UtilityClass.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(
                     "UPDATE orderitem SET orderId=?, menuId=?, quantity=?, price=? WHERE orderItemId=?")) {

            prepareStatement.setInt(1, orderItem.getOrderId());
            prepareStatement.setInt(2, orderItem.getMenuId());
            prepareStatement.setInt(3, orderItem.getQuantity());
            prepareStatement.setDouble(4, orderItem.getPrice());
            prepareStatement.setInt(5, orderItem.getOrderItemId());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrderItem(int orderItemId) {
        try (Connection connection = UtilityClass.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(
                     "DELETE FROM orderitem WHERE orderItemId=?")) {

            prepareStatement.setInt(1, orderItemId);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<OrderItem> getAllOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItemsList = new ArrayList<>();
        try (Connection connection = UtilityClass.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(
                     "SELECT * FROM orderitem WHERE orderId=?")) {

            prepareStatement.setInt(1, orderId);
            ResultSet res = prepareStatement.executeQuery();

            while (res.next()) {
                orderItemsList.add(new OrderItem(
                        res.getInt("orderItemId"),
                        res.getInt("orderId"),
                        res.getInt("menuId"),
                        res.getInt("quantity"),
                        res.getDouble("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItemsList;
    }
}
