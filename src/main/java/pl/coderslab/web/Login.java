package pl.coderslab.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.coderslab.dao.AdminDao;
import pl.coderslab.exception.UnauthorizedAdminException;
import pl.coderslab.model.Admin;

@WebServlet("/login")
public class Login extends HttpServlet {

  public static final AdminDao adminDao = new AdminDao();

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String email = req.getParameter("email");
    String password = req.getParameter("password");
    String errorMessage = null;
    try {
      Admin authenticatedAdmin = adminDao.authentication(email, password);
      if (authenticatedAdmin != null) {
        resp.sendRedirect("/");
        return;
      }
    } catch (UnauthorizedAdminException e) {
      errorMessage = "Wystąpił błąd podczas uwierzytelniania. Spróbuj ponownie później.";
    }
    errorMessage = "Nieprawidłowy email lub hasło";

    req.setAttribute("error", errorMessage);
    req.getRequestDispatcher("/login.jsp").forward(req, resp);
  }
}
