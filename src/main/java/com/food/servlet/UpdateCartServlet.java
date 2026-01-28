package com.food.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.food.dao.CartDAO;
import com.food.model.Cart;

@WebServlet("/updateCart")
public class UpdateCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CartDAO cartDao;
    
    @Override
    public void init() throws ServletException {
        cartDao = new CartDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();
        
        try {
            // Get parameters
            String menuIdStr = request.getParameter("menuId");
            String action = request.getParameter("action");
            String userIdStr = request.getParameter("userId");
            
            if (menuIdStr == null || action == null || userIdStr == null) {
                throw new ServletException("Missing required parameters");
            }
            
            int menuId = Integer.parseInt(menuIdStr);
            int userId = Integer.parseInt(userIdStr);
            
            // Get current cart item
            List<Cart> cartItems = cartDao.getCartsByUserId(userId);
            Cart targetItem = null;
            
            for (Cart item : cartItems) {
                if (item.getMenuId() == menuId) {
                    targetItem = item;
                    break;
                }
            }
            
            if (targetItem == null) {
                throw new ServletException("Cart item not found");
            }
            
            // Update quantity based on action
            int currentQuantity = targetItem.getQuantity();
            int newQuantity;
            
            if ("increase".equals(action)) {
                newQuantity = currentQuantity + 1;
            } else if ("decrease".equals(action)) {
                newQuantity = currentQuantity - 1;
            } else {
                throw new ServletException("Invalid action");
            }
            
            // Update or remove based on new quantity
            if (newQuantity <= 0) {
                // Remove item from cart
                cartDao.removeFromCart(userId, menuId);
                newQuantity = 0;
            } else {
                // Update quantity
                targetItem.setQuantity(newQuantity);
                cartDao.updateCartQuantity(targetItem);
            }
            
            // Calculate new total
            double newTotal = 0.0;
            List<Cart> updatedCart = cartDao.getCartsByUserId(userId);
            for (Cart item : updatedCart) {
                newTotal += item.getPrice() * item.getQuantity();
            }
            
            // Build response
            jsonResponse.put("success", true);
            jsonResponse.put("menuId", menuId);
            jsonResponse.put("quantity", newQuantity);
            jsonResponse.put("total", newTotal);
            
            out.print(jsonResponse.toString());
            
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("error", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(jsonResponse.toString());
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirect GET requests to POST
        doPost(request, response);
    }
}