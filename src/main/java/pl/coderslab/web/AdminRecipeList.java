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
import java.util.List;


@WebServlet("/app/recipe/list")
public class AdminRecipeList extends HttpServlet {
    public static final RecipeDao recipeDao = new RecipeDao();
    public static final Logger logger = LogManager.getLogger(AdminRecipeList.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        int adminId = (int) session.getAttribute("adminId");

        List<Recipe> recipes = recipeDao.getRecipesForAdmin(adminId);
        session.setAttribute("userRecipes", recipes);

        getServletContext().getRequestDispatcher("/user-recipes.jsp");
    }
}