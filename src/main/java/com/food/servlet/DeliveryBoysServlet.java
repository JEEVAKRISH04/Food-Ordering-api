package com.food.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.food.dao.UsersDAO;
import com.food.model.Users;
import com.google.gson.Gson;

@WebServlet("/deliveryBoys")
public class DeliveryBoysServlet extends HttpServlet {
    private UsersDAO userDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UsersDAO();
        gson = new Gson();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Get delivery boys from DAO
            List<Users> deliveryBoys = userDAO.getAllDeliveryBoys();
            System.out.println("Delivery Boys: " + deliveryBoys);
            
            // Convert to JSON
            String jsonResponse = gson.toJson(deliveryBoys);
            
            // Send response
            PrintWriter out = response.getWriter();
            out.print(jsonResponse);
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error retrieving delivery boys: " + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
}