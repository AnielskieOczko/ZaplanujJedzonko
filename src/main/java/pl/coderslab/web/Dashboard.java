package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.LastAddedPlanDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

    RecipeDao recipeDao = new RecipeDao();
    PlanDao planDao = new PlanDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        HttpSession session = req.getSession();
        // for testing - simulate logged admin (TO BE REMOVED)
//        session.setAttribute("adminId", 1);

        try {
            int adminId = (int) session.getAttribute("adminId");

            req.setAttribute("recipeCount", recipeDao.countRecipesForCurrentUser(req));
            req.setAttribute("planCount", planDao.getPlanCountByAdminId(adminId));
            req.setAttribute("mealList", planDao.getLastAddedPlan(adminId));

            getServletContext().getRequestDispatcher("/dashboard.jsp").forward(req, resp);

        } catch (Exception e) {
            // redirect to OpsSomethingWentWrong.jsp servlet
            req.setAttribute("errorMessage",e.getMessage());
            getServletContext().getRequestDispatcher("/OpsSomethingWentWrong.jsp").forward(req, resp);
        }
    }
}
