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
public class AdminPlanRecipeDelete extends HttpServlet {
  public static final PlanDao planDao = new PlanDao();
  public static final Logger logger = LogManager.getLogger(AdminDeletePlan.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    int recipePlanId = Integer.parseInt(req.getParameter("id"));
    req.setAttribute("recipePlanId", recipePlanId);
    getServletContext().getRequestDispatcher("app-del-recipe-from-plan-confirmation.jsp").forward(req, resp);
    logger.info(recipePlanId);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession();

    int adminId = (int) session.getAttribute("adminId");
    int recipePlanId = Integer.parseInt(req.getParameter("id"));
    int planId = planDao.getPlanId(recipePlanId);

    String deleteParam = req.getParameter("delete");
    if (deleteParam != null && deleteParam.equals("true")) {
      planDao.deleteRecipeFromPlan(recipePlanId);
      resp.sendRedirect("/app/plan/details?id=" + planId);
    } else {
      resp.sendRedirect("/app/plan/details?id=" + planId);
    }
  }
}
