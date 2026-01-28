package com.food.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Invalidate session
        HttpSession session = req.getSession(false); // Don't create if not exists
        if (session != null) {
            session.invalidate();
        }

        // Redirect to login page or home page
        resp.sendRedirect("login"); // Change to your login page if needed
    }
}
