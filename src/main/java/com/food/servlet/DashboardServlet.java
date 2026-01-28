package com.food.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.food.dao.MenuDAO;
import com.food.dao.OrderDAO;
import com.food.dao.RestaurantDAO;
import com.food.dao.UsersDAO;

@WebServlet("/dashboard-data")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // DAO objects
        OrderDAO ordersDAO = new OrderDAO();
        UsersDAO usersDAO = new UsersDAO();
        MenuDAO menuDAO = new MenuDAO();
        RestaurantDAO restaurantsDAO = new RestaurantDAO();

        // Get sizes
        int totalOrders = ordersDAO.getAllOrders().size();
        int totalUsers = usersDAO.getAllUsers().size();
        int totalMenus = menuDAO.getAllMenus().size();
        int totalRestaurants = restaurantsDAO.getAllRestaurants().size();

        // Create JSON
        String json = "{"
                + "\"totalOrders\":" + totalOrders + ","
                + "\"totalUsers\":" + totalUsers + ","
                + "\"totalMenus\":" + totalMenus + ","
                + "\"totalRestaurants\":" + totalRestaurants
                + "}";

        // Set response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
