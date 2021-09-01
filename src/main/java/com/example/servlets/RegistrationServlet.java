package com.example.servlets;

import com.example.dao.UserDAO;
import com.example.entity.User;
import com.mysql.cj.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration-servlet")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");
        String mail = req.getParameter("mail");
        User user = new User(login, password, mail);
        req.setAttribute("user", user);
        UserDAO userDAO = new UserDAO();
        if(userDAO.isThereLogin(login)){
            getServletContext().getRequestDispatcher("/userIsAlreadyExist.jsp").forward(req,resp);
        } else {
            if(!password.equals(password2)){
                getServletContext().getRequestDispatcher("/invalidPassword.jsp").forward(req,resp);
            } else {
                userDAO.add(user);
                getServletContext().getRequestDispatcher("/registrationIsSuccess.jsp").forward(req,resp);
                //resp.sendRedirect("/mainSignIn.jsp");
            }
        }
    }
}
