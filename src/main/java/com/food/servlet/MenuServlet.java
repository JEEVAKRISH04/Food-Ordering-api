package com.food.servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.food.dao.MenuDAO;
import com.food.model.Menu;

@WebServlet("/menus")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10,      // 10MB
maxRequestSize = 1024 * 1024 * 50)   // 50MB




public class MenuServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(MenuServlet.class.getName());
    private MenuDAO menuDAO;

    @Override
    public void init() throws ServletException {
        menuDAO = new MenuDAO(); 
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get restaurantId from request
        String restaurantIdStr = request.getParameter("restaurantId");

        if (restaurantIdStr == null || restaurantIdStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid restaurant ID");
            return; // Stop further execution
        }

        int restaurantId = Integer.parseInt(restaurantIdStr); // Parse restaurantId
        
        // Set restaurantId in session
        HttpSession session = request.getSession();
        session.setAttribute("restaurantId", restaurantId);

        // Fetch menu items based on restaurantId from the database
        List<Menu> menuList = menuDAO.getMenuByRestaurantId(restaurantId);

        // Store in request scope and forward to JSP
        request.setAttribute("menuList", menuList);

        request.getRequestDispatcher("menu.jsp").forward(request, response);
    }
    
    
  
    private static final String IMAGE_UPLOAD_DIR = "images";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Fetch other form fields
        String restaurantId = request.getParameter("restaurantid");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        String isAvailable = request.getParameter("isavailable");
        double ratings = 0.0;
        String ratingsParam = request.getParameter("ratings");
        if (ratingsParam != null && !ratingsParam.isEmpty()) {
            try {
                ratings = Double.parseDouble(ratingsParam);
            } catch (NumberFormatException e) {
                e.printStackTrace(); // Handle invalid input gracefully
            }
        }


        // Handle file upload
        Part filePart = request.getPart("imagefile");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // file name
        String uploadPath = getServletContext().getRealPath("") + File.separator + IMAGE_UPLOAD_DIR;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir(); // create images folder if not exist
        }

        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath); // Save file

        // Relative path to save in DB
        String imagePath = IMAGE_UPLOAD_DIR + "/" + fileName;

        // Now Save data to DB
        Menu menu = new Menu();
        menu.setRestaurantId(Integer.parseInt(restaurantId));
        menu.setName(name);
        menu.setPrice(Integer.parseInt(price));
        menu.setCategory(category);
        menu.setDescription(description);
        menu.setIsAvailable(Boolean.parseBoolean(isAvailable));
        menu.setRatings(ratings);
        menu.setImagePath(imagePath);

        // DAO call
        MenuDAO dao = new MenuDAO();
        dao.addMenu(menu);

        response.getWriter().write("Success");
    }
}
