package pl.coderslab.web;

import lombok.extern.java.Log;
import pl.coderslab.dao.BookDao;
import pl.coderslab.model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Do not change servlet address !!!
 */
@WebServlet("")
@Log
public class HomeServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    getServletContext().getRequestDispatcher("/home.jsp").forward(req, resp);
    log.info("Home servlet is working.");
  }
}
