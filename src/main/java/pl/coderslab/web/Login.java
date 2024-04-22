package pl.coderslab.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.dao.AdminDao;
import pl.coderslab.exception.UnauthorizedAdminException;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



@WebServlet("/login")
public class Login extends HttpServlet {
  public static final Logger log = LogManager.getLogger(Login.class);
  public static final AdminDao adminDao = new AdminDao();

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String email = req.getParameter("email");
    String password = req.getParameter("password");

    try {
      Admin authenticatedAdmin = adminDao.authentication(email, password);

      HttpSession loginSession = req.getSession();
      loginSession.setAttribute("adminId", authenticatedAdmin.getId());
      loginSession.setAttribute("adminName", authenticatedAdmin.getFirstName());

        log.info("Session Id: {}", loginSession.getId());
        log.info("UserId: {}", loginSession.getAttribute("adminId"));

      resp.sendRedirect("/app/dashboard");

    } catch (UnauthorizedAdminException e) {
      e.printStackTrace();
      String errorMessage = "Nieprawidłowy email lub hasło";
      req.setAttribute("error", errorMessage);
      req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
  }

}