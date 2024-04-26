package pl.coderslab.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.dao.RecipeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/recipes")
public class HomeRecipeList extends HttpServlet {
    public static final Logger logger = LogManager.getLogger(HomeRecipeList.class);
    RecipeDao recipeDao = new RecipeDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("recipeList", recipeDao.findAll());
        getServletContext().getRequestDispatcher("/home-recipe-list.jsp").forward(req, resp);
    }
}
