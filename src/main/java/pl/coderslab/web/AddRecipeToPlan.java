package pl.coderslab.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// TODO implementation of adding new recipe to plan
@WebServlet("/app/recipe/plan/add")
public class AddRecipeToPlan extends HttpServlet {
    public static final Logger logger = LogManager.getLogger(AddRecipeToPlan.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/app-schedules-meal-recipe.jsp").forward(req, resp);
    }
}
