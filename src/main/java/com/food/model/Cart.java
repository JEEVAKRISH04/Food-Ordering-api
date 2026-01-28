package com.food.model;

import java.sql.Timestamp;

public class Cart {
    private int cartId;
    private String menuName;
    private double price;	
    private int quantity;
    private String imagePath;
    private int restaurantId;
    private int userId;
    private int menuId;
    private Timestamp addedAt;  // Changed from createdAt to addedAt to match DB column

    public Cart() {
    	
    }
    // Update constructor
    public Cart(int cartId, String menuName, double price, int quantity, String imagePath, int restaurantId, int userId,
    int menuId, Timestamp addedAt) {
        super();
        this.cartId = cartId;
        this.menuName = menuName;
        this.price = price;
        this.quantity = quantity;
        this.imagePath = imagePath;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.menuId = menuId;
        this.addedAt = addedAt;
    }
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public void setAddedAt(java.sql.Timestamp addedAt) {
	    this.addedAt = addedAt;
	}

	public java.sql.Timestamp getAddedAt() {
	    return addedAt;
	}
	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", menuName=" + menuName + ", price=" + price + ", quantity=" + quantity
				+ ", imagePath=" + imagePath + ", restaurantId=" + restaurantId + ", userId=" + userId + ", menuId="
				+ menuId + ", addedAt=" + addedAt + "]";
	}
    
    
}