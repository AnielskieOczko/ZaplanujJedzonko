package pl.coderslab.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dto.PlanDto;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/plan/edit")
public class AdminEditPlan extends HttpServlet {
    public static final Logger logger = LogManager.getLogger(AdminEditPlan.class);
    PlanDao planDao = new PlanDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int planId = Integer.parseInt(req.getParameter("id"));
        PlanDto planToEdit = planDao.getPlanForAdmin(planId);

        req.setAttribute("planToEdit", planToEdit);

        getServletContext().getRequestDispatcher("/app-edit-schedules.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int adminId = (int) req.getSession().getAttribute("adminId");
        int planId = Integer.parseInt(req.getParameter("id"));

        String planName = req.getParameter("plan-name");
        String planDescription = req.getParameter("plan-description");

        Plan editedPlan = new Plan(planName, planDescription, adminId);
        editedPlan.setId(planId);

        int isUpdated = planDao.update(editedPlan);

        if (isUpdated == 1) {
            logger.info("Plan updated {}", editedPlan);
            resp.sendRedirect("/app/plan/list");

        } else {
            logger.info("Plan not updated: {}", editedPlan);
            resp.sendRedirect("/app/plan/list");
        }
    }
}
