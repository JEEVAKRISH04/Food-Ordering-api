package com.food.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.food.dao.CartDAO;

@WebServlet("/removeFromCart")
public class RemoveFromCartServlet extends HttpServlet {
    private CartDAO cartDao;

    @Override
    public void init() {
        cartDao = new CartDAO(); // Initialize DAO
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String menuIdStr = request.getParameter("menuId");
        String userIdStr = request.getParameter("userId");
        String action = request.getParameter("action");
        
        System.out.println("DEBUG: All parameters: " + request.getParameterMap().keySet());
        System.out.println("DEBUG: Raw menuId parameter: " + menuIdStr);
        System.out.println("DEBUG: Raw userId parameter: " + userIdStr);
        
        if (action != null && action.equals("clearAll")) {
            // Handle clear all cart
            try {
                int userId = Integer.parseInt(userIdStr);
                cartDao.clearCart(userId);
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            // Handle individual item removal
            if (menuIdStr == null || userIdStr == null || menuIdStr.isEmpty() || userIdStr.isEmpty()) {
                System.out.println("DEBUG: Missing parameters - menuId: " + menuIdStr + ", userId: " + userIdStr);
                response.sendRedirect("addtocart?action=viewcart");
                return;
            }
            
            try {
                int menuId = Integer.parseInt(menuIdStr);
                int userId = Integer.parseInt(userIdStr);
                
                System.out.println("DEBUG: Removing item - menuId: " + menuId + ", userId: " + userId);
                
                // Call DAO to remove from cart
                cartDao.removeFromCart(userId, menuId);
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
        
        // Get userId for redirect
        int userId = 1; // Default
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            // If userId can't be parsed, try to get from session
            HttpSession session = request.getSession();
            Integer sessionUserId = (Integer) session.getAttribute("userId");
            if (sessionUserId != null) {
                userId = sessionUserId;
            }
        }
        
        response.sendRedirect("addtocart?action=viewcart&userId=" + userId);
    }
}