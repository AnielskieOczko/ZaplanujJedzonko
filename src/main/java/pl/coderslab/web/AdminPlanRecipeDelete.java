package pl.coderslab.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.dao.PlanDao;

@WebServlet("/app/plan/recipe/delete?")
public class AdminPlanRecipeDelete extends HttpServlet {

  public static Logger logger = LogManager.getLogger(AdminPlanRecipeDelete.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    int recipePlanId = Integer.parseInt(req.getParameter("id"));
    req.setAttribute("recipePlanId", recipePlanId);
    logger.info(recipePlanId);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    PlanDao planDao = new PlanDao();
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
