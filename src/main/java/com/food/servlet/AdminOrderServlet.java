package com.food.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.food.dao.OrderDAO;
import com.food.model.Orders;
import com.google.gson.Gson;

@WebServlet("/ordersdata")
public class AdminOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderDAO ordersDao;

    @Override
    public void init() throws ServletException {
        ordersDao = new OrderDAO(); // Initialize your DAO here
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Orders> orderList = ordersDao.getAllOrders();
            System.out.println(orderList);
        // Convert List to JSON using Gson
        Gson gson = new Gson();
        String jsonOrders = gson.toJson(orderList);

        PrintWriter out = response.getWriter();
        out.print(jsonOrders);
        out.flush();
    }
}
