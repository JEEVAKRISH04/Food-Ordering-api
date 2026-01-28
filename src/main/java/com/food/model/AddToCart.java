package com.food.model;

public class AddToCart {
    private int menuId;
    private String name;
    private double price;
    private String description;
    private String imagePath;
    private int quantity;
    private double totalPrice;

    // Constructor
    public AddToCart(int menuId, String name, double price, String description, String imagePath, int quantity, double totalPrice) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
