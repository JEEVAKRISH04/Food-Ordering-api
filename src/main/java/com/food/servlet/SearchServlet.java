package com.food.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.food.dao.MenuDAO;
import com.food.dao.OrderDAO;
import com.food.dao.RestaurantDAO;
import com.food.model.Menu;
import com.food.model.Orders;
import com.food.model.Restaurants;
import com.google.gson.Gson;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchQuery = request.getParameter("searchQuery");

        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // DAOs
        RestaurantDAO restaurantDAO = new RestaurantDAO();
        MenuDAO menuDAO = new MenuDAO();
        OrderDAO orderDAO = new OrderDAO();

        // Get results
        List<Restaurants> restaurants = restaurantDAO.searchRestaurants(searchQuery);
        List<Menu> menus = menuDAO.searchMenus(searchQuery);
        List<Orders> orders = orderDAO.searchOrders(searchQuery);

        // Put in Map
        Map<String, Object> searchResults = new HashMap<>();
        searchResults.put("restaurants", restaurants);
        searchResults.put("menus", menus);
        searchResults.put("orders", orders);

        // Convert to JSON
        String jsonResponse = new Gson().toJson(searchResults);

        // Response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }
}
