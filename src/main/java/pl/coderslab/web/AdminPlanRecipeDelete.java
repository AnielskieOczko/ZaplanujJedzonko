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

@WebServlet("/app/plan/recipe/delete")
public class AdminPlanRecipeDelete extends HttpServlet {

  public static Logger logger = LogManager.getLogger(AdminPlanRecipeDelete.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    int recipePlanId = Integer.parseInt(req.getParameter("id"));
    req.setAttribute("recipePlanId", recipePlanId);
    logger.info(recipePlanId);
    System.out.println(recipePlanId);
  }
}
