package pl.coderslab.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/add")
public class AddNewRecipe extends HttpServlet {
    RecipeDao recipeDao = new RecipeDao();
    AdminDao adminDao = new AdminDao();
    public static final Logger logger = LogManager.getLogger(IsLoggedInFilter.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int adminId = (int) session.getAttribute("adminId");

        req.setAttribute("adminName", adminDao.read(adminId).getFirstName());
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
            Recipe newRecipe = recipeDao.create(recipe);
            System.out.println(newRecipe);
            // TODO: change redirect to /app/recipe
            resp.sendRedirect("/app/dashboard");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
