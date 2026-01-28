package com.food.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.food.dao.UsersDAO;
import com.food.model.Users;
import com.tap.enums.UserRole;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private UsersDAO userDao;

    public void init() throws ServletException {
        userDao = new UsersDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String roleParam = req.getParameter("role");

        UserRole role = null;
        if (roleParam != null && !roleParam.isEmpty()) {
            role = UserRole.valueOf(roleParam); // Convert to Enum
        }

        System.out.println("Login Attempt: " + username + ", Role: " + role);

        // Pass role properly!
        Users user = userDao.getUserByUserNamePasswordAndRole(username, password, role);
        HttpSession session = req.getSession();

        Integer attempts = (Integer) session.getAttribute("attempts");
        if (attempts == null) {
            attempts = 0;
        }

        if (user != null) {
            System.out.println("User Found: " + user.getName() + ", userId: " + user.getUserId());

            // Set session attributes
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("role", user.getRole().name());

            // Reset attempts after successful login
            session.setAttribute("attempts", 0);

            System.out.println("User logged in successfully, setting session attributes.");

            // Redirect based on role
            if (user.getRole() == UserRole.CUSTOMER) {
                resp.sendRedirect("Restaurantpage");
            } else if (user.getRole() == UserRole.ADMIN) {
                resp.sendRedirect("AdminDashboard");
            } else if (user.getRole() == UserRole.DELIVERY_BOY) {
                resp.sendRedirect("DeliveryboyDashboard"); // Example, you can customize
            } else {
                // Default fallback
                resp.sendRedirect("HomePage.jsp");
            }

        } else {
            System.out.println("User Not Found. Incrementing attempts.");

            attempts++;
            session.setAttribute("attempts", attempts);
            System.out.println("Current Attempts: " + attempts);

            if (attempts >= 5) {
                System.out.println("Max Attempts Reached. Redirecting to Login.jsp with error.");
                resp.sendRedirect("Login.jsp?error=max_attempts");
            } else {
                System.out.println("Invalid Credentials. Redirecting to Login.jsp.");
                resp.sendRedirect("Login.jsp?error=invalid_credentials");
            }
        }
    }
}
