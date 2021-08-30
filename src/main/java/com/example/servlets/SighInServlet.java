package com.example.servlets;

import com.example.dao.UserDAO;
import com.example.entity.User;
import com.example.exceptions.DaoException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/sign-in-servlet")
public class SighInServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserDAO userDAO = new UserDAO();
        User user = new User();

        try {
            user = userDAO.findByLoginAndPassword(login, password);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        if(!user.getLogin().equals(null)){
            Cookie cookie = new Cookie("login", user.getLogin());
            Cookie cookie2 = new Cookie("password", user.getPassword());
            Cookie cookie3 = new Cookie("mail", user.getMail());
            resp.addCookie(cookie);
            resp.addCookie(cookie2);
            resp.addCookie(cookie3);
            getServletContext().getRequestDispatcher("/mainSignIn.jsp").forward(req,resp);
        }


    }


}
