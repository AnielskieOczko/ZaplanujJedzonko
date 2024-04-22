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

@WebServlet("/app/recipe/details")
public class RecipeDetails extends HttpServlet {
    public static final Logger logger = LogManager.getLogger(RecipeDetails.class);
    public static final RecipeDao recipeDao = new RecipeDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        int recipeId = Integer.parseInt(req.getParameter("id"));
        Recipe recipe = recipeDao.read(recipeId);

        session.setAttribute("recipeDetails", recipe);
        session.setAttribute("recipeIngredients", recipe.getIngredientList());

        getServletContext().getRequestDispatcher("/admin-recipe-details.jsp").forward(req, resp);
    }
}
