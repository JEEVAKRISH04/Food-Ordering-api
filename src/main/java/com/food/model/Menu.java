package com.food.model;

public class Menu {
    private int menuId;
    private int restaurantId;
    private String name;
    private double price;
    private String imagePath;
    private String category;
    private String description;
    private Boolean isAvailable;
    private double ratings;
    
    

    public Menu() {
    }



	public Menu(int menuId, int restaurantId, String name, double price, String imagePath, String category,
			String description, Boolean isAvailable, double ratings) {
		super();
		this.menuId = menuId;
		this.restaurantId = restaurantId;
		this.name = name;
		this.price = price;
		this.imagePath = imagePath;
		this.category = category;
		this.description = description;
		this.isAvailable = isAvailable;
		this.ratings = ratings;
	}



	public int getMenuId() {
		return menuId;
	}



	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}



	public int getRestaurantId() {
		return restaurantId;
	}



	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
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



	public String getImagePath() {
		return imagePath;
	}



	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Boolean getIsAvailable() {
		return isAvailable;
	}



	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}



	public double getRatings() {
		return ratings;
	}



	public void setRatings(double ratings) {
		this.ratings = ratings;
	}



	@Override
	public String toString() {
		return "Menu [menuId=" + menuId + ", restaurantId=" + restaurantId + ", name=" + name + ", price=" + price
				+ ", imagePath=" + imagePath + ", category=" + category + ", description=" + description
				+ ", isAvailable=" + isAvailable + ", ratings=" + ratings + "]";
	}

	
    
}
