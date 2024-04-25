package pl.coderslab.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.exception.NotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/app/recipe/delete")
public class AdminDeleteRecipe extends HttpServlet {
    public static final Logger logger = LogManager.getLogger(AdminDeleteRecipe.class);
    RecipeDao recipeDao = new RecipeDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int recipeId = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("recipeId", recipeId);
        getServletContext().getRequestDispatcher("/app-recipe-delete-confirmation.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int recipeId = Integer.parseInt(req.getParameter("id"));
        String deleteRecipe = req.getParameter("delete");
        System.out.println(recipeId);

        if (deleteRecipe.equals("true")) {
            try {
                recipeDao.delete(recipeId);
                logger.info("Recipe with id {} removed", recipeId);
                resp.sendRedirect("/app/recipe/list");
            } catch (Exception e) {
                logger.error("Recipe with id {} NOT removed, error: {}", recipeId, e.getMessage());
                resp.sendRedirect("/ops");
            }

        } else {
            logger.info("Delete operation of recipe with id {} cancelled", recipeId);
            resp.sendRedirect("/app/recipe/list");
        }

    }
}
