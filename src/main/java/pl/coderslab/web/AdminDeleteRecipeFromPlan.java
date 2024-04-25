package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.exception.NotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/plan/recipe/delete")
public class AdminDeleteRecipeFromPlan extends HttpServlet {
    public static final PlanDao planDao = new PlanDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int recipePlanId = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("recipePlanId", recipePlanId);
        getServletContext().getRequestDispatcher("/app-recipe-plan-delete-confirmation.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int recipePlanId = Integer.parseInt(req.getParameter("id"));
        Integer planId = planDao.getPlanIdByRecipePlanId(recipePlanId);

        String isRecipeToDelete = req.getParameter("delete");

        if ("true".equals(isRecipeToDelete)) {
            try {
                planDao.deleteRecipeFromPlan(recipePlanId);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }

        resp.sendRedirect("/app/plan/details?id=" + planId);
    }
}
