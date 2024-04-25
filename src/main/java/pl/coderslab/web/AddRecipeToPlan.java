package pl.coderslab.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.dto.PlanDto;
import pl.coderslab.model.DayName;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

// TODO implementation of adding new recipe to plan
@WebServlet("/app/recipe/plan/add")
public class AddRecipeToPlan extends HttpServlet {
    public static final Logger logger = LogManager.getLogger(AddRecipeToPlan.class);

    PlanDao planDao = new PlanDao();
    RecipeDao recipeDao = new RecipeDao();
    DayNameDao dayNameDao = new DayNameDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        int adminId = (int) session.getAttribute("adminId");

        List<PlanDto> plans = planDao.getAllPlansForAdmin(adminId);
        List<Recipe> recipes = recipeDao.getRecipesForAdmin(adminId);
        List<DayName> dayNames = dayNameDao.findAll();

        session.setAttribute("plans", plans);
        session.setAttribute("recipes", recipes);
        session.setAttribute("dayNames", dayNames);

        getServletContext().getRequestDispatcher("/app-schedules-meal-recipe.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int planId = Integer.parseInt(req.getParameter("choosePlan"));
        int recipeId = Integer.parseInt(req.getParameter("recipe"));
        int dayId = Integer.parseInt(req.getParameter("day"));
        int mealNumber = Integer.parseInt(req.getParameter("meal-number"));
        String mealName = req.getParameter("meal-name");

        planDao.setAddRecipeToPlan(recipeId, mealName, mealNumber, dayId, planId);
        resp.sendRedirect("/app/recipe/plan/add");
    }
}
