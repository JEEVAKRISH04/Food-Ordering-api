package com.food.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.food.model.AddToCart;
import com.google.gson.JsonObject;

public class UpdateCartQuantity extends HttpServlet{
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int menuId = Integer.parseInt(request.getParameter("menuId"));
	        String action = request.getParameter("action");

	        HttpSession session = request.getSession();
	        List<AddToCart> cart = (List<AddToCart>) session.getAttribute("cart");

	        int updatedQuantity = 0;
	        double totalPrice = 0;

	        if (cart != null) {
	            for (AddToCart item : cart) {
	                if (item.getMenuId() == menuId) {
	                    if ("increase".equals(action)) {
	                        item.setQuantity(item.getQuantity() + 1);
	                    } else if ("decrease".equals(action) && item.getQuantity() > 1) {
	                        item.setQuantity(item.getQuantity() - 1);
	                    }
	                    updatedQuantity = item.getQuantity();
	                }
	                totalPrice += item.getQuantity() * item.getPrice();
	            }
	        }

	        // Prepare JSON response
	        JsonObject json = new JsonObject();
	        json.addProperty("quantity", updatedQuantity);
	        json.addProperty("total", totalPrice);

	        response.setContentType("application/json");
	        PrintWriter out = response.getWriter();
	        out.print(json.toString());
	        out.flush();
	    }
}
