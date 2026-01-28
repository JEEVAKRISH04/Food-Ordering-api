package com.food.model;

import java.sql.Timestamp;
import com.tap.enums.OrderStatus;

public class Orders {
    private int orderId;
    private int userId;  // Customer ID
    private String userName;  
    private int restaurantId;
    private String restaurantName;
    private double totalAmount;
    private OrderStatus status;
    private Timestamp orderDate;
    private String deliveryAddress;

    // New Fields
    private Integer deliveryBoyId;  // Nullable, as some orders may not have a delivery boy assigned
    private String deliveryBoyName;

    public Orders() {
    }

    // Updated constructor with delivery boy details
    public Orders(int orderId, int userId, String userName, int restaurantId, String restaurantName, 
                  double totalAmount, OrderStatus status, Timestamp orderDate, String deliveryAddress,
                  Integer deliveryBoyId, String deliveryBoyName) {
        super();
        this.orderId = orderId;
        this.userId = userId;
        this.userName = userName;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
        this.deliveryAddress = deliveryAddress;
        this.deliveryBoyId = deliveryBoyId;
        this.deliveryBoyName = deliveryBoyName;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Integer getDeliveryBoyId() {
        return deliveryBoyId;
    }

    public void setDeliveryBoyId(Integer deliveryBoyId) {
        this.deliveryBoyId = deliveryBoyId;
    }

    public String getDeliveryBoyName() {
        return deliveryBoyName;
    }

    public void setDeliveryBoyName(String deliveryBoyName) {
        this.deliveryBoyName = deliveryBoyName;
    }

    @Override
    public String toString() {
        return "Orders [orderId=" + orderId + ", userId=" + userId + ", userName=" + userName 
                + ", restaurantId=" + restaurantId + ", restaurantName=" + restaurantName 
                + ", totalAmount=" + totalAmount + ", status=" + status 
                + ", orderDate=" + orderDate + ", deliveryAddress=" + deliveryAddress 
                + ", deliveryBoyId=" + deliveryBoyId + ", deliveryBoyName=" + deliveryBoyName + "]";
    }
}
