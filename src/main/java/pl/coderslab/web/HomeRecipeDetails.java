package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/recipe/details")
public class HomeRecipeDetails extends HttpServlet {
    public static final RecipeDao recipeDao = new RecipeDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int recipeId = Integer.parseInt(req.getParameter("id"));
        Recipe recipe = recipeDao.read(recipeId);
        if (recipe != null) {
            session.setAttribute("recipe", recipe);
            session.setAttribute("ingredients", recipe.getIngredientList());
            getServletContext().getRequestDispatcher("/home-recipe-details.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/ops");
        }
    }
}
