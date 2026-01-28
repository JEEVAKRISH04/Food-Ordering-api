package com.food.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.food.dao.RestaurantDAO;
import com.food.model.Restaurants;

@WebServlet("/fetchRestaurants")
public class FetchRestaurantServlet extends HttpServlet {

    RestaurantDAO restaurantDao = new RestaurantDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Restaurants> restaurants = restaurantDao.getAllRestaurants();

        JSONArray restaurantArray = new JSONArray();

        for (Restaurants restaurant : restaurants) {
            JSONObject obj = new JSONObject();
            obj.put("id", restaurant.getRestaurantId());
            obj.put("name", restaurant.getName());
            restaurantArray.put(obj);
        }

        response.setContentType("application/json");
        response.getWriter().write(restaurantArray.toString());
    }
}
