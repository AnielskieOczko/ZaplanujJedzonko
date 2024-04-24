package pl.coderslab.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/app/recipe/edit")
public class AdminEditRecipe extends HttpServlet {
    public static final Logger logger = LogManager.getLogger(AdminEditRecipe.class);
    public static final RecipeDao recipeDao = new RecipeDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int recipeId = Integer.parseInt(req.getParameter("id"));
        Recipe recipe = recipeDao.read(recipeId);
        session.setAttribute("recipe", recipe);
        session.setAttribute("recipeIngredients", recipe.getIngredientList());
        getServletContext().getRequestDispatcher("/app-edit-recipe.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int adminId = (int) session.getAttribute("adminId");

        String recipeName = req.getParameter("recipe-name");
        int recipeId = Integer.parseInt(req.getParameter("id"));
        String recipeIngredients = req.getParameter("recipe-ingredients");
        String recipeDescription = req.getParameter("recipe-description");
        int recipePreparationTime = Integer.parseInt(req.getParameter("recipe-preparation-time"));
        String recipePreparation = req.getParameter("recipe-preparation");

        Recipe editedRecipe = new Recipe(recipeName, recipeIngredients, recipeDescription, recipePreparationTime, recipePreparation, adminId);
        editedRecipe.setId(recipeId);

        logger.info(editedRecipe);
    }
}
