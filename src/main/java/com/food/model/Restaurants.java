package com.food.model;

public class Restaurants {
    private int restaurantId;
    private String name;
    private String email;
    private String phoneNo;
    private String address;
    private double rating;
    private String imagePath;
    private String cusineType;

    public Restaurants() {
    }

	public Restaurants(int restaurantId, String name, String email, String phoneNo, String address, double rating, String imagePath, String cusineType) {
		super();
		this.restaurantId = restaurantId;
		this.name = name;
		this.email = email;
		this.phoneNo = phoneNo;
		this.address = address;
		this.rating = rating;
		this.imagePath = imagePath;
		this.cusineType = cusineType;
		
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public String getCusineType() {
		return cusineType;
	}
	
	public void setCusineType(String cusineType) {
		this.cusineType = cusineType;
	}

	@Override
	public String toString() {
		return "Restaurants [restaurantId=" + restaurantId + ", name=" + name + ", email=" + email + ", phoneNo="
				+ phoneNo + ", address=" + address + ", rating=" + rating + "]";
	}
    
}
