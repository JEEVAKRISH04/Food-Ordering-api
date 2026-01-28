package com.food.servlet;

import com.food.dao.UsersDAO;
import com.food.model.Users;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/usersData")
public class UserServlet extends HttpServlet {

    private UsersDAO usersDAO;

    @Override
    public void init() throws ServletException {
        usersDAO = new UsersDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Users> userList = usersDAO.getAllUsers();

        // Convert to JSON using Gson
        Gson gson = new Gson();
        String json = gson.toJson(userList);

        // Set response type
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String userIdStr = request.getParameter("userId");

            if (userIdStr != null && !userIdStr.trim().isEmpty()) {
                int userId = Integer.parseInt(userIdStr);
                usersDAO.deleteUser(userId);
                
                // Send a success response
                response.setContentType("application/json");
                response.getWriter().write("{\"status\":\"success\"}");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                // Send an error response if userId is missing
                response.setContentType("application/json");
                response.getWriter().write("{\"status\":\"error\",\"message\":\"User ID is required\"}");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            // Send an error response for server-side errors
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
