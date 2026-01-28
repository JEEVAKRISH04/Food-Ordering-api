package com.food.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.food.dao.OrderDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

@WebServlet("/assign-delivery-boy")
public class AssignDeliveryBoyServlet extends HttpServlet {
    private OrderDAO orderDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        orderDAO = new OrderDAO();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Read JSON input
            BufferedReader reader = request.getReader();
            String jsonInputString = reader.lines().collect(Collectors.joining(System.lineSeparator()));

            log("Received JSON: " + jsonInputString);

            // Parse JSON with additional error handling
            JsonObject jsonInput = parseJsonSafely(jsonInputString);

            // Extract order and delivery boy IDs with null checks
            int orderId = extractIntSafely(jsonInput, "orderId", "Order ID is required");
            int deliveryBoyId = extractIntSafely(jsonInput, "deliveryBoyId", "Delivery Boy ID is required");

            log("Order ID: " + orderId);
            log("Delivery Boy ID: " + deliveryBoyId);

            // Attempt to assign delivery boy
            boolean assignmentResult = orderDAO.assignDeliveryBoyAndConfirmOrder(orderId, deliveryBoyId);

            // Prepare response
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", assignmentResult);

            if (assignmentResult) {
                jsonResponse.addProperty("message", "Delivery boy assigned successfully");
                log("Successfully assigned delivery boy " + deliveryBoyId + " to order " + orderId);
            } else {
                jsonResponse.addProperty("message", "Failed to assign delivery boy. Please check order and user status.");
                log("Failed to assign delivery boy " + deliveryBoyId + " to order " + orderId);
            }

            // Send response
            sendJsonResponse(response, jsonResponse);

        } catch (IllegalArgumentException e) {
            // Handle validation errors
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // Log and send generic error response
            log("Unexpected error during delivery boy assignment", e);
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Unexpected error: " + e.getMessage());
        }
    }

    // Safely parse JSON with error handling
    private JsonObject parseJsonSafely(String jsonInputString) {
        try {
            return gson.fromJson(jsonInputString, JsonObject.class);
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Invalid JSON format", e);
        }
    }

    // Safely extract integer with validation
    private int extractIntSafely(JsonObject jsonInput, String key, String errorMessage) {
        if (jsonInput == null || !jsonInput.has(key) || jsonInput.get(key).isJsonNull()) {
            throw new IllegalArgumentException(errorMessage);
        }
        
        try {
            return jsonInput.get(key).getAsInt();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid " + key + " format");
        }
    }

    // Send JSON response helper method
    private void sendJsonResponse(HttpServletResponse response, JsonObject jsonResponse) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            out.print(gson.toJson(jsonResponse));
            out.flush();
        }
    }

    // Send error response helper method
    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        JsonObject errorResponse = new JsonObject();
        errorResponse.addProperty("success", false);
        errorResponse.addProperty("message", message);

        try (PrintWriter out = response.getWriter()) {
            out.print(gson.toJson(errorResponse));
            out.flush();
        }
    }

   
}