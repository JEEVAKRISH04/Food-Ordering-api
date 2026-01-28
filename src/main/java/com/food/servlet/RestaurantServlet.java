package com.food.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.food.dao.RestaurantDAO;
import com.food.model.Restaurants;

@WebServlet("/restaurants")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
	    maxFileSize = 1024 * 1024 * 10,       // 10MB
	    maxRequestSize = 1024 * 1024 * 50     // 50MB
	)
public class RestaurantServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RestaurantServlet.class.getName());
    private RestaurantDAO restaurantDAO;

    @Override
    public void init() throws ServletException {
        restaurantDAO = new RestaurantDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Fetching all restaurants...");

        try {
            // Fetching all restaurants
            List<Restaurants> allRestaurants = restaurantDAO.getAllRestaurants();

            // Get session
            HttpSession session = req.getSession();
            session.setAttribute("restaurantList", allRestaurants);

            // Get selected restaurantId from request parameter (if user clicked any restaurant)
            String restaurantIdParam = req.getParameter("restaurantId");
            if (restaurantIdParam != null) {
                try {
                    int selectedRestaurantId = Integer.parseInt(restaurantIdParam);
                    session.setAttribute("restaurantId", selectedRestaurantId);
                    LOGGER.info("Selected Restaurant ID: " + selectedRestaurantId);
                } catch (NumberFormatException e) {
                    LOGGER.warning("Invalid restaurantId parameter: " + restaurantIdParam);
                }
            }

            // Forwarding to JSP
            RequestDispatcher rd = req.getRequestDispatcher("restaurant.jsp");
            rd.forward(req, resp);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching restaurant data", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving restaurant data");
        }
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            // Retrieve text data
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phoneNo = request.getParameter("phoneno");
            String address = request.getParameter("address");
            String ratingStr = request.getParameter("rating");
            String cusineType = request.getParameter("cusinetype");

            // Parse rating
            double rating = 0.0;
            if (ratingStr != null && !ratingStr.isEmpty()) {
                rating = Double.parseDouble(ratingStr);
            }

            // ===== IMAGE UPLOAD =====
            Part filePart = request.getPart("imagepath");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            // Folder where images will be saved (Change path as per project structure)
            String uploadPath = getServletContext().getRealPath("") + "images";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            // Full file path
            String filePath = uploadPath + File.separator + fileName;

            // Write file to folder
            filePart.write(filePath);

            // Save relative path to DB (like images/taj.jpg)
            String imagePath = "images/" + fileName;

            // ===== Create Restaurant Object =====
            Restaurants restaurant = new Restaurants();
            restaurant.setName(name);
            restaurant.setEmail(email);
            restaurant.setPhoneNo(phoneNo);
            restaurant.setAddress(address);
            restaurant.setRating(rating);
            restaurant.setImagePath(imagePath);
            restaurant.setCusineType(cusineType);

            restaurantDAO.addRestaurant(restaurant);

            // JSON success
            String json = "{ \"status\": \"success\", \"message\": \"Restaurant added successfully!\" }";
            out.print(json);

        } catch (Exception e) {
            e.printStackTrace();
            String json = "{ \"status\": \"error\", \"message\": \"Failed to add restaurant.\" }";
            out.print(json);
        }
    }

}
