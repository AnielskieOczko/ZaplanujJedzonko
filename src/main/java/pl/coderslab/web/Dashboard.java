package pl.coderslab.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/app/dashboard")
public class Dashboard extends HttpServlet {

    RecipeDao recipeDao = new RecipeDao();
    PlanDao planDao = new PlanDao();
    public static final Logger logger = LogManager.getLogger(IsLoggedInFilter.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        HttpSession session = req.getSession();

        try {
            int adminId = (int) session.getAttribute("adminId");

            req.setAttribute("recipeCount", recipeDao.countRecipesByAdminId(adminId));
            req.setAttribute("planCount", planDao.getPlanCountByAdminId(adminId));
            req.setAttribute("mealList", planDao.getLastAddedPlan(adminId));

            getServletContext().getRequestDispatcher("/dashboard.jsp").forward(req, resp);

        } catch (Exception e) {
            // redirect to opsSomethingWentWrong.jsp servlet
            logger.error(e.getMessage());
            getServletContext().getRequestDispatcher("/opsSomethingWentWrong.jsp").forward(req, resp);
        }
    }
}
