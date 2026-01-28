package com.food.servlet;

import com.food.dao.OrderDAO;
import com.food.model.Orders;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/pending-orders")
public class AdminDeliverAgentServlet extends HttpServlet {
    private OrderDAO orderDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        orderDAO = new OrderDAO();
        gson = new Gson();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Get pending orders from DAO
            List<Orders> pendingOrders = orderDAO.getOrdersWithPendingStatus();
            System.out.println(pendingOrders);

            // Convert to JSON
            String jsonResponse = gson.toJson(pendingOrders);

            // Send response
            PrintWriter out = response.getWriter();
            out.print(jsonResponse);
            out.flush();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error retrieving pending orders: " + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
}