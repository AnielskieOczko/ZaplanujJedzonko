package pl.coderslab.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/recipe/add")
public class AddNewRecipe extends HttpServlet {
    RecipeDao recipeDao = new RecipeDao();
    PlanDao planDao = new PlanDao();
    AdminDao adminDao = new AdminDao();
    public static final Logger logger = LogManager.getLogger(IsLoggedInFilter.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        session.setAttribute("adminId", 1);
        int adminId = (int) session.getAttribute("adminId");

        req.setAttribute("adminName", adminDao.read(adminId).getFirstName());

        getServletContext().getRequestDispatcher("/addNewRecipe.jsp").forward(req, resp);
    }
}
