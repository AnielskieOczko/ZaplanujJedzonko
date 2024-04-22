package pl.coderslab.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpRequest;

@WebServlet("/app/plan/add")
public class AddNewPlan extends HttpServlet {

    public static final Logger logger = LogManager.getLogger(IsLoggedInFilter.class);
    PlanDao planDao = new PlanDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/app-add-schedules.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String planName = req.getParameter("plan-name");
        String planDescription = req.getParameter("plan-description");
        int adminId = (int) req.getSession().getAttribute("adminId");

        try {
            Plan planToAdd = new Plan(planName, planDescription, adminId);
            planDao.create(planToAdd);
            logger.info("plan added to DB: {}", planToAdd);
//            TODO: change redirect to plan list view
            resp.sendRedirect("/app/dashboard");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
