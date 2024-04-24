package pl.coderslab.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.dao.PlanDao;

@WebServlet("/app/plan/recipe/delete")
public class AdminRecipeDelete extends HttpServlet {

  public static final Logger logger = LogManager.getLogger(AdminPlanDetails.class);
  public static final PlanDao planDao = new PlanDao();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    getServletContext().getRequestDispatcher("/app-del-recipe-from-plan-confirmation.jsp")
        .forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String action = req.getParameter("action");

    if ("OK".equals(action)) {
//      planDao.deleteRecipeFromPlan(???);
      resp.sendRedirect(req.getContextPath() + "/app/plan/details");

    } else if ("Anuluj".equals(action)) {
      resp.sendRedirect(req.getContextPath() + "/app/plan/details");
    } else {
      resp.sendRedirect(req.getContextPath() + "/app/plan/details");
    }

  }
}
