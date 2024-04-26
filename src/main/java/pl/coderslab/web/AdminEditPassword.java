package pl.coderslab.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// TODO implementation of admin password edit functionality
@WebServlet("/app/admin/password/edit")
public class AdminEditPassword extends HttpServlet {
    public static final Logger logger = LogManager.getLogger(AdminEditPassword.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/app-edit-password.jsp").forward(req, resp);
    }
}
