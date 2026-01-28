package com.food.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.food.model.OrderItem;
import com.food.model.Orders;
import com.food.utility.UtilityClass;
import com.tap.enums.OrderStatus;

public class OrderDAO {

    private static final String INSERT_ORDER_SQL = 
        "INSERT INTO orders (userid, restaurantid, totalamount, status, orderdate, delivery_address) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String INSERT_ORDER_ITEM_SQL = 
        "INSERT INTO orderitem (orderid, menuid, quantity, price) VALUES (?, ?, ?, ?)";

    private static final String SELECT_ALL_QUERY = 
    	    "SELECT o.orderid, o.userid, u.name AS username, o.restaurantid, r.name AS restaurantname, " +
    	    "o.totalamount, o.status, o.orderdate, o.delivery_address " +
    	    "FROM orders o " +
    	    "JOIN users u ON o.userid = u.userid " +
    	    "JOIN restaurant r ON o.restaurantid = r.restaurantid";



    // Existing methods...

    public int addOrder(Orders order) throws SQLException {
        try (Connection conn = UtilityClass.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_ORDER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, order.getUserId());
            pstmt.setInt(2, order.getRestaurantId());
            pstmt.setDouble(3, order.getTotalAmount());
            pstmt.setString(4, order.getStatus().toString());
            pstmt.setTimestamp(5, order.getOrderDate());
            pstmt.setString(6, order.getDeliveryAddress());
            
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public void addOrderItem(OrderItem item) throws SQLException {
        Map<Integer, Object> paramMap = new HashMap<>();
        paramMap.put(1, item.getOrderId());
        paramMap.put(2, item.getMenuId());
        paramMap.put(3, item.getQuantity());
        paramMap.put(4, item.getPrice());

        UtilityClass.updateObject(INSERT_ORDER_ITEM_SQL, paramMap);
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) throws SQLException {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT menuid, quantity, price FROM orderitem WHERE orderid = ?";
        try (Connection conn = UtilityClass.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setOrderId(orderId);
                item.setMenuId(rs.getInt("menuid"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                orderItems.add(item);
            }
        }
        return orderItems;
    }

    public List<Orders> searchOrders(String keyword) {
        List<Orders> orderList = new ArrayList<>();
        String searchKeyword = "%" + keyword + "%";
        String query = "SELECT * FROM orders WHERE orderid LIKE ? OR status LIKE ?";

        try (Connection conn = UtilityClass.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, searchKeyword);
            pstmt.setString(2, searchKeyword);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Orders order = new Orders();
                order.setOrderId(rs.getInt("orderid"));
                order.setUserId(rs.getInt("userid"));
                order.setRestaurantId(rs.getInt("restaurantid"));
                order.setTotalAmount(rs.getDouble("totalamount"));
                order.setOrderDate(rs.getTimestamp("orderdate"));
                order.setDeliveryAddress(rs.getString("delivery_address"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status").toUpperCase()));

                orderList.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderList;
    }

    public List<Orders> getAllOrders() {
        List<Orders> orderList = new ArrayList<>();
        try {
            List<Map<String, String>> result = UtilityClass.executeQuery(SELECT_ALL_QUERY);
            for (Map<String, String> row : result) {
                Orders order = new Orders();
                order.setOrderId(Integer.parseInt(row.get("orderid")));
                order.setUserId(Integer.parseInt(row.get("userid")));
                order.setUserName(row.get("username"));
                order.setRestaurantId(Integer.parseInt(row.get("restaurantid")));
                order.setRestaurantName(row.get("restaurantname"));
                order.setTotalAmount(Double.parseDouble(row.get("totalamount")));
                order.setStatus(OrderStatus.valueOf(row.get("status").toUpperCase()));
                order.setOrderDate(Timestamp.valueOf(row.get("orderdate")));
                order.setDeliveryAddress(row.get("delivery_address"));
                
                orderList.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderList;
    }


    public Orders getOrderById(int orderId) {
        Orders order = null;
        String query = "SELECT * FROM orders WHERE orderid = ?";
        
        try (Connection conn = UtilityClass.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                order = new Orders();
                order.setOrderId(rs.getInt("orderid"));
                order.setUserId(rs.getInt("userid"));
                order.setRestaurantId(rs.getInt("restaurantid"));
                order.setTotalAmount(rs.getDouble("totalamount"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status").toUpperCase()));
                order.setOrderDate(rs.getTimestamp("orderdate"));
                order.setDeliveryAddress(rs.getString("delivery_address"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return order;
    }

    private static final String GET_PENDING_ORDERS_SQL = 
            "SELECT * FROM orders WHERE status = 'CONFIRMED' AND userid IS NULL";
    
    private static final String GET_ASSIGNED_ORDERS_SQL = 
    	    "SELECT o.orderid, o.userid AS customer_id, c.name AS customer_name, " +
    	    "o.restaurantid, r.name AS restaurant_name, o.totalamount, o.status, " +
    	    "o.orderdate, o.delivery_address, d.userid AS deliveryboy_id, d.name AS deliveryboy_name " +
    	    "FROM orders o " +
    	    "JOIN users c ON o.userid = c.userid AND c.role = 'CUSTOMER' " +
    	    "LEFT JOIN users d ON o.orderid = d.userid AND d.role = 'DELIVERY_BOY' " + 
    	    "JOIN restaurant r ON o.restaurantid = r.restaurantid " +
    	    "WHERE o.status IN ('CONFIRMED', 'OUT_FOR_DELIVERY')";



    
    private static final String GET_RESTAURANT_NAME_SQL = 
            "SELECT restaurantname FROM restaurants WHERE restaurantid = ?";
    
    private static final String ASSIGN_DELIVERY_BOY_SQL = 
            "UPDATE orders SET userid = ? WHERE orderid = ?";
    
    private static final String UPDATE_ORDER_STATUS_SQL = 
            "UPDATE orders SET status = ? WHERE orderid = ?";
    
    private static final String GET_DELIVERY_BOY_FOR_ORDER_SQL = 
            "SELECT userid FROM orders WHERE orderid = ?";
    
    private static final String GET_ORDERS_FOR_DELIVERY_BOY_SQL = 
            "SELECT * FROM orders WHERE userid = ?";
    
    /**
     * Get all pending orders (confirmed but not assigned to a delivery boy)
     * 
     * @return List of pending orders
     */
    public List<Orders> getPendingOrders() {
        List<Orders> pendingOrders = new ArrayList<>();
        
        try (Connection conn = UtilityClass.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_PENDING_ORDERS_SQL)) {
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Orders order = new Orders();
                order.setOrderId(rs.getInt("orderid"));
                order.setUserId(rs.getInt("userid"));
                order.setRestaurantId(rs.getInt("restaurantid"));
                order.setTotalAmount(rs.getDouble("totalamount"));
                order.setOrderDate(rs.getTimestamp("orderdate"));
                order.setDeliveryAddress(rs.getString("delivery_address"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status").toUpperCase()));
                pendingOrders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return pendingOrders;
    }
    
    /**
     * Get all orders that have been assigned to delivery boys
     * 
     * @return List of assigned orders
     */
    public List<Orders> getAssignedOrders() {
        List<Orders> assignedOrders = new ArrayList<>();
        String sql = "SELECT o.orderid, o.userid AS customer_id, c.name AS customer_name, "
                   + "o.restaurantid, r.name AS restaurant_name, o.totalamount, o.status, "
                   + "o.orderdate, o.delivery_address, o.deliveryboyid, d.name AS deliveryboy_name "
                   + "FROM orders o "
                   + "JOIN users c ON o.userid = c.userid AND c.role = 'CUSTOMER' "
                   + "JOIN restaurant r ON o.restaurantid = r.restaurantid "
                   + "LEFT JOIN users d ON o.deliveryboyid = d.userid AND d.role = 'DELIVERY_BOY' "
                   + "WHERE o.deliveryboyid IS NOT NULL AND o.status IN ('CONFIRMED', 'OUT_FOR_DELIVERY')";

        try (Connection conn = UtilityClass.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Orders order = new Orders();
                order.setOrderId(rs.getInt("orderid"));
                order.setUserId(rs.getInt("customer_id"));
                order.setUserName(rs.getString("customer_name"));
                order.setRestaurantId(rs.getInt("restaurantid"));
                order.setRestaurantName(rs.getString("restaurant_name"));
                order.setTotalAmount(rs.getDouble("totalamount"));
                order.setOrderDate(rs.getTimestamp("orderdate"));
                order.setDeliveryAddress(rs.getString("delivery_address"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status").toUpperCase()));
                
                // Assign Delivery Boy Details if available
                int deliveryBoyId = rs.getInt("deliveryboyid");
                if (!rs.wasNull()) {
                    order.setDeliveryBoyId(deliveryBoyId);
                    order.setDeliveryBoyName(rs.getString("deliveryboy_name"));
                }

                assignedOrders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assignedOrders;
    }

    /**
     * Get restaurant name by ID
     * 
     * @param restaurantId Restaurant ID
     * @return Restaurant name
     */
    public String getRestaurantName(int restaurantId) {
        String name = "Unknown";
        
        try (Connection conn = UtilityClass.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_RESTAURANT_NAME_SQL)) {
            
            ps.setInt(1, restaurantId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                name = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return name;
    }
    
    public boolean assignDeliveryBoyAndConfirmOrder(int orderId, int deliveryBoyId) {
        String sql = "UPDATE orders SET deliveryboyid = ?, status = 'CONFIRMED' WHERE orderid = ?";

        try (Connection conn = UtilityClass.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Log the details before execution
            System.out.println("Attempting to assign Delivery Boy:");
            System.out.println("Order ID: " + orderId);
            System.out.println("Delivery Boy ID: " + deliveryBoyId);

            ps.setInt(1, deliveryBoyId);
            ps.setInt(2, orderId);

            int updatedRows = ps.executeUpdate();
            
            if (updatedRows > 0) {
                System.out.println("Successfully updated " + updatedRows + " row(s)");
                return true;
            } else {
                System.out.println("No rows were updated. Check if Order ID exists.");
                return false;
            }

        } catch (SQLException e) {
            // More detailed error logging
            System.err.println("SQL Error in assignDeliveryBoyAndConfirmOrder:");
            System.err.println("Order ID: " + orderId);
            System.err.println("Delivery Boy ID: " + deliveryBoyId);
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidOrder(Connection conn, int orderId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM orders WHERE orderid = ? AND status IN ('PENDING', 'CONFIRMED')";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private boolean isValidDeliveryBoy(Connection conn, int deliveryBoyId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE userid = ? AND role = 'DELIVERY_BOY'";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, deliveryBoyId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    /**
     * Update order status
     * 
     * @param orderId Order ID
     * @param status New status
     * @return true if update successful, false otherwise
     */
    public boolean updateOrderStatus(int orderId, String status) {
        try (Connection conn = UtilityClass.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_ORDER_STATUS_SQL)) {
            
            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get delivery boy ID for an order
     * 
     * @param orderId Order ID
     * @return Delivery boy ID or -1 if not assigned
     */
    public int getDeliveryBoyForOrder(int orderId) {
        int deliveryBoyId = -1;
        
        try (Connection conn = UtilityClass.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_DELIVERY_BOY_FOR_ORDER_SQL)) {
            
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                deliveryBoyId = rs.getInt("userid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return deliveryBoyId;
    }
    
    /**
     * Get orders assigned to a specific delivery boy
     * 
     * @param deliveryBoyId Delivery boy ID
     * @return List of orders assigned to the delivery boy
     */
    public List<Orders> getOrdersForDeliveryBoy(int deliveryBoyId) {
        List<Orders> orderList = new ArrayList<>();
        
        try (Connection conn = UtilityClass.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_ORDERS_FOR_DELIVERY_BOY_SQL)) {
            
            ps.setInt(1, deliveryBoyId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Orders order = new Orders();
                order.setOrderId(rs.getInt("orderid"));
                order.setUserId(rs.getInt("userid"));
                order.setRestaurantId(rs.getInt("restaurantid"));
                order.setTotalAmount(rs.getDouble("totalamount"));
                order.setOrderDate(rs.getTimestamp("orderdate"));
                order.setDeliveryAddress(rs.getString("delivery_address"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status").toUpperCase()));
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return orderList;
    }
    public List<Orders> getOrdersWithPendingStatus() {
        List<Orders> pendingOrders = new ArrayList<>();
        
        final String GET_PENDING_STATUS_ORDERS_SQL = 
            "SELECT o.orderid, o.userid, u.name AS username, o.restaurantid, r.name AS restaurantname, " +
            "o.totalamount, o.status, o.orderdate, o.delivery_address " +
            "FROM orders o " +
            "JOIN users u ON o.userid = u.userid " +
            "JOIN restaurant r ON o.restaurantid = r.restaurantid " +
            "WHERE o.status = 'PENDING'";
        
        try (Connection conn = UtilityClass.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_PENDING_STATUS_ORDERS_SQL)) {
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Orders order = new Orders();
                order.setOrderId(rs.getInt("orderid"));
                order.setUserId(rs.getInt("userid"));
                order.setUserName(rs.getString("username"));
                order.setRestaurantId(rs.getInt("restaurantid"));
                order.setRestaurantName(rs.getString("restaurantname"));
                order.setTotalAmount(rs.getDouble("totalamount"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status").toUpperCase()));
                order.setOrderDate(rs.getTimestamp("orderdate"));
                order.setDeliveryAddress(rs.getString("delivery_address"));
                
                pendingOrders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return pendingOrders;
    }
}
