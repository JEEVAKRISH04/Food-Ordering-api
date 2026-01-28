package com.food.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.food.model.Cart;
import com.food.utility.UtilityClass;

public class CartDAO {
	public int insertCart(Cart cart) throws SQLException {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    int result = 0;
	    
	    try {
	        conn = UtilityClass.getConnection();
	        
	        // First check if this menu item already exists in this user's cart
	        String checkQuery = "SELECT cartid, quantity FROM cart WHERE menuid = ? AND userid = ?";
	        stmt = conn.prepareStatement(checkQuery);
	        stmt.setInt(1, cart.getMenuId());
	        stmt.setInt(2, cart.getUserId());
	        rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            // Item exists, update the quantity
	            int cartId = rs.getInt("cartid");
	            int currentQuantity = rs.getInt("quantity");
	            int newQuantity = currentQuantity + cart.getQuantity();
	            
	            rs.close();
	            stmt.close();
	            
	            String updateQuery = "UPDATE cart SET quantity = ? WHERE cartid = ?";
	            stmt = conn.prepareStatement(updateQuery);
	            stmt.setInt(1, newQuantity);
	            stmt.setInt(2, cartId);
	            result = stmt.executeUpdate();
	            
	            System.out.println("Updated existing cart item. New quantity: " + newQuantity);
	        } else {
	            // Item doesn't exist, insert new row
	            stmt.close();
	            
	            String insertQuery = "INSERT INTO cart (menuid, userid, quantity, price, imagepath, addedat, menuname, restaurantid) "
	                      + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	            stmt = conn.prepareStatement(insertQuery);
	            stmt.setInt(1, cart.getMenuId());
	            stmt.setInt(2, cart.getUserId());
	            stmt.setInt(3, cart.getQuantity());
	            stmt.setDouble(4, cart.getPrice());
	            stmt.setString(5, cart.getImagePath());
	            stmt.setTimestamp(6, cart.getAddedAt());
	            stmt.setString(7, cart.getMenuName());
	            stmt.setInt(8, cart.getRestaurantId());
	            
	            result = stmt.executeUpdate();
	            System.out.println("Inserted new cart item");
	        }
	        
	        // Commit if required
	        if (!conn.getAutoCommit()) {
	            conn.commit();
	        }
	        
	    } finally {
	        if (rs != null) rs.close();
	        if (stmt != null) stmt.close();
	        if (conn != null) conn.close();
	    }
	    
	    return result;
	}
    // --- Get Cart by ID ---
    public Cart getCartById(int cartId) {
        String sql = "SELECT * FROM cart WHERE cart_id = " + cartId;
        List<Map<String, String>> result = UtilityClass.executeQuery(sql);
        if (result.isEmpty()) {
            return null;
        }

        Map<String, String> row = result.get(0);
        return mapToCart(row);
    }

    // --- Get All Carts for User ---
    public List<Cart> getCartsByUserId(int userId) {
        String sql = "SELECT * FROM cart WHERE userid = " + userId;
        List<Map<String, String>> result = UtilityClass.executeQuery(sql);
        List<Cart> cartList = new ArrayList<>();
        for (Map<String, String> row : result) {
            cartList.add(mapToCart(row));
        }
        return cartList;
    }

    public int updateCartQuantity(Cart cart) throws SQLException {
        String sql = "UPDATE cart SET quantity = ? WHERE menuid = ? AND userid = ?";
        Map<Integer, Object> paramMap = new HashMap<>();
        paramMap.put(1, cart.getQuantity());
        paramMap.put(2, cart.getMenuId());
        paramMap.put(3, cart.getUserId());
        
        return UtilityClass.updateObject(sql, paramMap);
    }

    public int removeFromCart(int userId, int menuId) throws SQLException {
        System.out.println("DEBUG: Attempting to remove item with menuId: " + menuId + ", userId: " + userId);

        if (menuId <= 0 || userId <= 0) {
            System.out.println("ERROR: Invalid parameters - menuId: " + menuId + ", userId: " + userId);
            return 0;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int result = 0;

        try {
            conn = UtilityClass.getConnection();

            // Step 1: Check current quantity
            String checkQuery = "SELECT quantity FROM cart WHERE menuid = ? AND userid = ?";
            stmt = conn.prepareStatement(checkQuery);
            stmt.setInt(1, menuId);
            stmt.setInt(2, userId);
            rs = stmt.executeQuery();
// dao
            //comment unchanged
            if (rs.next()) {
                int quantity = rs.getInt("quantity");
                System.out.println("DEBUG: Current quantity = " + quantity);

                if (quantity > 1) {
                    // Step 2: Decrement quantity
                    stmt.close();
                    String updateQuery = "UPDATE cart SET quantity = quantity - 1 WHERE menuid = ? AND userid = ?";
                    stmt = conn.prepareStatement(updateQuery);
                    stmt.setInt(1, menuId);
                    stmt.setInt(2, userId);
                    result = stmt.executeUpdate();
                    System.out.println("DEBUG: Decremented quantity by 1");
                } else {
                    // Step 3: Quantity = 1, delete row
                    stmt.close();
                    String deleteQuery = "DELETE FROM cart WHERE menuid = ? AND userid = ?";
                    stmt = conn.prepareStatement(deleteQuery);
                    stmt.setInt(1, menuId);
                    stmt.setInt(2, userId);
                    result = stmt.executeUpdate();
                    System.out.println("DEBUG: Deleted item from cart");
                }

                // Commit if required
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            } else {
                System.out.println("DEBUG: No matching cart item found.");
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return result;
    }
    public int clearCart(int userId) throws SQLException {
        if (userId <= 0) {
            System.out.println("ERROR: Invalid userId: " + userId);
            return 0;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;

        try {
            conn = UtilityClass.getConnection();
            stmt = conn.prepareStatement("DELETE FROM cart WHERE userid = ?");
            stmt.setInt(1, userId);

            result = stmt.executeUpdate();
            System.out.println("DEBUG: Cleared " + result + " items from cart for user " + userId);

            if (!conn.getAutoCommit()) {
                conn.commit();
            }
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return result;
    }

 // --- Helper Method: Convert Map to Cart Object ---
    private Cart mapToCart(Map<String, String> row) {
        Cart cart = new Cart();

        // --- Cart ID ---
        String cartIdStr = row.get("cartid");
        if (cartIdStr != null && !cartIdStr.isEmpty()) {
            cart.setCartId(Integer.parseInt(cartIdStr));
        } else {
            cart.setCartId(0); // default value
        }

        // --- Menu Name ---
        cart.setMenuName(row.getOrDefault("menuname", ""));

        // --- Price ---
        String priceStr = row.get("price");
        if (priceStr != null && !priceStr.isEmpty()) {
            cart.setPrice(Double.parseDouble(priceStr));
        } else {
            cart.setPrice(0.0); // default price
        }

        // --- Quantity ---
        String quantityStr = row.get("quantity");
        if (quantityStr != null && !quantityStr.isEmpty()) {
            cart.setQuantity(Integer.parseInt(quantityStr));
        } else {
            cart.setQuantity(1); // default quantity
        }

        // --- Image Path ---
        cart.setImagePath(row.getOrDefault("imagepath", ""));

        // --- Restaurant ID ---
        String restaurantIdStr = row.get("restaurantid");
        if (restaurantIdStr != null && !restaurantIdStr.isEmpty()) {
            cart.setRestaurantId(Integer.parseInt(restaurantIdStr));
        } else {
            cart.setRestaurantId(0);
        }

        // --- User ID ---
        String userIdStr = row.get("userid");
        if (userIdStr != null && !userIdStr.isEmpty()) {
            cart.setUserId(Integer.parseInt(userIdStr));
        } else {
            cart.setUserId(0);
        }

        // --- Menu ID ---
        String menuIdStr = row.get("menuid");
        if (menuIdStr != null && !menuIdStr.isEmpty()) {
            cart.setMenuId(Integer.parseInt(menuIdStr));
        } else {
            cart.setMenuId(0);
        }

        // --- Added At ---
        String addedAtStr = row.get("addedat");
        if (addedAtStr != null && !addedAtStr.isEmpty()) {
            cart.setAddedAt(Timestamp.valueOf(addedAtStr));
        } else {
            cart.setAddedAt(new Timestamp(System.currentTimeMillis())); // default current time
        }

        return cart;
    }

}
