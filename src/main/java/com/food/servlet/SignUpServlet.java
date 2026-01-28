package com.food.servlet;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.food.dao.UsersDAO;
import com.food.model.Users;
import com.tap.enums.UserRole;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {

    private UsersDAO usersDAO;

    public void init() throws ServletException {
        usersDAO = new UsersDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phoneNo");
        String username = req.getParameter("username");
        String role = req.getParameter("role");
        String password = req.getParameter("password");
        String address = req.getParameter("address");

        if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            resp.sendRedirect("Login.jsp?error=missingField");
            return;
        }

        if (usersDAO.isUserExists(username)) {
            resp.sendRedirect("Login.jsp?error=userExists");
            return;
        }

        Users user = new Users();
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNo(phone);
        user.setUserName(username);
        user.setRole(UserRole.valueOf(role.toUpperCase()));
        user.setPassword(password);
        user.setAddress(address);
   

        usersDAO.addUser(user);;

        resp.sendRedirect("login");
    }
}