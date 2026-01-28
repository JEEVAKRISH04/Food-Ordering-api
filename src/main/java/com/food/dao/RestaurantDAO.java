package com.food.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.food.model.Restaurants;
import com.food.utility.UtilityClass;

public class RestaurantDAO {

    // SQL Queries
    private static final String INSERT_QUERY = "INSERT INTO restaurant (name, email, phoneNo, address, rating, imagePath, cusineType) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM restaurant WHERE restaurantId = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM restaurant";
    private static final String SELECT_QUERY = "SELECT * FROM restaurant WHERE restaurantId = ?";
    private static final String UPDATE_QUERY = "UPDATE restaurant SET name = ?, email = ?, phoneNo = ?, address = ?, rating = ?, imagePath = ?, cusineType = ? WHERE restaurantId = ?";

    // Add a new restaurant
    public void addRestaurant(Restaurants restaurant) {
        Map<Integer, Object> params = new HashMap<>();
       
        params.put(1, restaurant.getName());
        params.put(2, restaurant.getEmail());
        params.put(3, restaurant.getPhoneNo());
        params.put(4, restaurant.getAddress());
        params.put(5, restaurant.getRating());
        params.put(6, restaurant.getImagePath());
        params.put(7, restaurant.getCusineType());


        try {
            UtilityClass.updateObject(INSERT_QUERY, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a restaurant by ID
    public void deleteRestaurant(int restaurantId) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, restaurantId);

        try {
            UtilityClass.updateObject(DELETE_QUERY, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	    // Get all restaurants
	    public List<Restaurants> getAllRestaurants() {
	        List<Restaurants> restaurantList = new ArrayList<>();
	        try {
	            List<Map<String, String>> result = UtilityClass.executeQuery(SELECT_ALL_QUERY);
	            for (Map<String, String> row : result) {
	                int restaurantId = Integer.parseInt(row.get("restaurantid"));
	
	                Restaurants restaurant = new Restaurants(
	                        restaurantId,
	                        row.get("name"),
	                        row.get("email"),
	                        row.get("phoneno"),
	                        row.get("address"),
	                        row.get("rating") != null ? Double.parseDouble(row.get("rating")) : 0.0,
	                        row.get("imagepath"),
	                        row.get("cusinetype")
	                );
	                restaurantList.add(restaurant);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return restaurantList;
	    }

    // Update a restaurant
    public void updateRestaurant(Restaurants restaurant) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, restaurant.getName());
        params.put(2, restaurant.getEmail());
        params.put(3, restaurant.getPhoneNo());
        params.put(4, restaurant.getAddress());
        params.put(5, restaurant.getRating());
        params.put(6, restaurant.getImagePath());
        params.put(7, restaurant.getCusineType());
        params.put(8, restaurant.getRestaurantId());

        try {
            UtilityClass.updateObject(UPDATE_QUERY, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 // Search restaurants
    public List<Restaurants> searchRestaurants(String keyword) {
        List<Restaurants> restaurantList = new ArrayList<>();
        String query = "SELECT * FROM restaurant WHERE name ILIKE ? OR cusineType ILIKE ? OR address ILIKE ?";

        List<Object> params = new ArrayList<>();
        String searchKeyword = "%" + keyword + "%";
        params.add(searchKeyword);
        params.add(searchKeyword);
        params.add(searchKeyword);

        try {
            List<Map<String, String>> result = UtilityClass.executeQueryForPreview(query, params);
            for (Map<String, String> row : result) {
                int restaurantId = Integer.parseInt(row.get("restaurantid"));
                Restaurants restaurant = new Restaurants(
                        restaurantId,
                        row.get("name"),
                        row.get("email"),
                        row.get("phoneno"),
                        row.get("address"),
                        row.get("rating") != null ? Double.parseDouble(row.get("rating")) : 0.0,
                        row.get("imagepath"),
                        row.get("cusinetype")
                );
                restaurantList.add(restaurant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurantList;
    }

}
