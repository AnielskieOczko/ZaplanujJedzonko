package pl.coderslab.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class Register extends HttpServlet {
    public static final Logger logger = LogManager.getLogger(Register.class);
    public static final AdminDao adminDao = new AdminDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    /**
     Set default value for superAdmin to 0 - it means it is regular user
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("first-name");
        String lastName = req.getParameter("surname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        Admin admin = new Admin(firstName, lastName, email, password, 0, 1);

        var returned = adminDao.create(admin);
        if (returned != null) {
            resp.sendRedirect("/login");
        } else {
            String errorMessage = "Podany email jest już w użyciu";
            req.setAttribute("errorMessage", errorMessage);
            getServletContext().getRequestDispatcher("/registration.jsp").forward(req, resp);
        }
    }
}
