package com.food.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.food.dao.OrderDAO;
import com.food.dao.PaymentDAO;
import com.food.model.AddToCart;
import com.food.model.Cart;
import com.food.model.OrderItem;
import com.food.model.Orders;
import com.food.model.Payment;
import com.tap.enums.OrderStatus;
import com.tap.enums.PaymentMethod;
import com.tap.enums.PaymentStatus;

@WebServlet("/OrderConformationpage")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private OrderDAO orderDAO;
    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        paymentDAO = new PaymentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        List<Cart> cart = (List<Cart>) session.getAttribute("dbCart");
        Integer userId = (Integer) session.getAttribute("userId");
        
        // Debug information
        System.out.println("Cart from session: " + cart);
        System.out.println("UserId from session: " + userId);

        if (cart == null || cart.isEmpty() || userId == null) {
            System.out.println("Cart is null or empty: " + (cart == null || cart.isEmpty()));
            System.out.println("UserId is null: " + (userId == null));
            
            request.setAttribute("errorMessage", "Your cart is empty or session has expired");
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
            return;
        }

        // Use restaurantId from the first cart item instead of session
        Integer restaurantId = cart.get(0).getRestaurantId();
        System.out.println("RestaurantId from cart: " + restaurantId);

        if (restaurantId == null) {
            request.setAttribute("errorMessage", "Restaurant information is missing");
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
            return;
        }

        String address = request.getParameter("address");
        String paymentMethodStr = request.getParameter("paymentMethod");
        
        if (paymentMethodStr == null || paymentMethodStr.isEmpty()) {
            request.setAttribute("errorMessage", "Please select a payment method");
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
            return;
        }

        PaymentMethod paymentMethod;
        try {
            paymentMethod = PaymentMethod.valueOf(paymentMethodStr);
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", "Invalid payment method");
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
            return;
        }

        // Calculate total amount from cart items
        double totalAmount = 0;
        for (Cart item : cart) {
            totalAmount += item.getPrice() * item.getQuantity();
        }

        try {
            // Create order
            Orders order = new Orders();
            order.setUserId(userId);
            order.setRestaurantId(restaurantId);
            order.setTotalAmount(totalAmount);
            order.setStatus(OrderStatus.PENDING);
            order.setOrderDate(new Timestamp(System.currentTimeMillis()));
            order.setDeliveryAddress(address);

            // Add order and get generated ID
            int orderId = orderDAO.addOrder(order);
            order.setOrderId(orderId);
            
            System.out.println("Order created with ID: " + orderId);

            // Add order items
            for (Cart cartItem : cart) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(orderId);
                orderItem.setMenuId(cartItem.getMenuId());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getPrice());
                orderDAO.addOrderItem(orderItem);
            }

            // Create and add payment
            Payment payment = new Payment();
            payment.setOrderId(orderId);
            payment.setUserId(userId);
            payment.setPaymentMethod(paymentMethod);
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
            payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));

            paymentDAO.addPayment(payment);
            
            // Get order items from the database for confirmation page
            List<OrderItem> orderItems = orderDAO.getOrderItemsByOrderId(orderId);
            
            // Set all required attributes
            request.setAttribute("orderId", orderId);
            request.setAttribute("price", totalAmount);
            request.setAttribute("deliveryAddress", address);
            request.setAttribute("paymentMethod", paymentMethod.toString());
            request.setAttribute("orderItems", orderItems);
            
            // Clear the cart after successful order
            session.removeAttribute("dbCart");
            
            // Forward to confirmation page
            request.getRequestDispatcher("/orderConfirmation.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while processing your order: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr != null) {
            try {
                int orderId = Integer.parseInt(orderIdStr);

                // Fetch order details again
                Orders order = orderDAO.getOrderById(orderId);
                List<OrderItem> orderItems = orderDAO.getOrderItemsByOrderId(orderId);

                if (order != null) {
                    request.setAttribute("orderId", orderId);
                    request.setAttribute("price", order.getTotalAmount());
                    request.setAttribute("deliveryAddress", order.getDeliveryAddress());
                    request.setAttribute("paymentMethod", paymentDAO.getPaymentMethodByOrderId(orderId));
                    request.setAttribute("orderItems", orderItems);
                } else {
                    request.setAttribute("errorMessage", "Order not found.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Unable to load order details: " + e.getMessage());
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
        }
        request.getRequestDispatcher("/orderConfirmation.jsp").forward(request, response);
    }
}