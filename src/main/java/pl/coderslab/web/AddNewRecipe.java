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

@WebServlet("/app/recipe/add")
public class AddNewRecipe extends HttpServlet {
    public static final Logger logger = LogManager.getLogger(AddNewRecipe.class);
    RecipeDao recipeDao = new RecipeDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/addNewRecipe.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session =  req.getSession();
        int adminId = (int) session.getAttribute("adminId");

        String name = req.getParameter("recipe-name");
        String desc = req.getParameter("recipe-desc");
        int prepTime = Integer.parseInt(req.getParameter("recipe-prep-time"));
        String prepDesc = req.getParameter("recipe-prep-desc");
        String ingredients = req.getParameter("recipe-ingredients");

        try {
            Recipe recipe = new Recipe(name, ingredients, desc, prepTime, prepDesc, adminId);
            recipeDao.create(recipe);
            resp.sendRedirect("/app/recipe/list");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
