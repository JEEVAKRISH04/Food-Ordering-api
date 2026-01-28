package com.food.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.food.dao.CartDAO;
import com.food.dao.MenuDAO;
import com.food.model.AddToCart;
import com.food.model.Cart;
import com.food.model.Menu;

@WebServlet("/addtocart")
public class AddToCartServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(AddToCartServlet.class.getName());
    private CartDAO cartDao;
   
    @Override
    public void init() throws ServletException {
        cartDao = new CartDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AddToCartServlet: doPost method called");
        logger.info("AddToCartServlet: doPost method called");
    	System.out.println("inside dopost");
        try {
            // Step 1: Get all parameters from frontend
            String menuIdStr = request.getParameter("menuId");
            String userIdStr = request.getParameter("userId");
            String quantityStr = request.getParameter("quantity");
            String priceStr = request.getParameter("price");
            String imagePath = request.getParameter("imagePath");
            String menuName = request.getParameter("name");
            String restaurantIdStr = request.getParameter("restaurantId");

            // Debugging logs
            logger.info("Received Data - menuId: " + menuIdStr + ", userId: " + userIdStr + ", quantity: " + quantityStr 
                        + ", price: " + priceStr + ", imagePath: " + imagePath + ", menuName: " + menuName 
                        + ", restaurantId: " + restaurantIdStr);

            // Step 2: Check if parameters are not null
            if (menuIdStr == null || userIdStr == null || quantityStr == null || priceStr == null || 
                imagePath == null || menuName == null || restaurantIdStr == null) {
                logger.warning("One or more parameters are missing!");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Step 3: Parse necessary data
            int menuId = Integer.parseInt(menuIdStr);
            int userId = Integer.parseInt(userIdStr);
            int quantity = Integer.parseInt(quantityStr);
            
            // Parse price as double instead of int
            double price = Double.parseDouble(priceStr);
            
            int restaurantId = Integer.parseInt(restaurantIdStr);

            // Step 4: Create Cart object
            Cart cart = new Cart();
            cart.setMenuId(menuId);
            cart.setUserId(userId);
            cart.setQuantity(quantity);
            cart.setPrice(price);
            cart.setImagePath(imagePath);
            cart.setMenuName(menuName);
            cart.setRestaurantId(restaurantId);

            // Set current timestamp
            java.sql.Timestamp addedAt = java.sql.Timestamp.valueOf(LocalDateTime.now());
            cart.setAddedAt(addedAt);



            // Step 5: Call DAO to insert
            int rowsInserted = cartDao.insertCart(cart);

            if (rowsInserted > 0) {
                logger.info("Cart item inserted successfully.");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Item added to cart successfully.");
            } else {
                logger.warning("Failed to insert cart item.");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (NumberFormatException e) {
            logger.severe("Invalid number format: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error: " + e.getMessage());
        } catch (SQLException e) {
            logger.severe("SQL Exception: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Database error: " + e.getMessage());
        } catch (Exception e) {
            logger.severe("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Server error: " + e.getMessage());
        }
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            userId = 1; // Default user ID (adjust as needed)
        }

        // Fetch cart items from database
        List<Cart> cartList = cartDao.getCartsByUserId(userId);
        System.out.println(cartList);

        // Set in request scope (existing)
        request.setAttribute("dbCart", cartList);

        // ✅ ALSO store in session scope (new line)
        session.setAttribute("dbCart", cartList);

        // Calculate total price
        double total = 0;
        for (Cart item : cartList) {
            total += item.getPrice() * item.getQuantity();
        }
        request.setAttribute("total", total);

        // Forward to cart.jsp
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

}