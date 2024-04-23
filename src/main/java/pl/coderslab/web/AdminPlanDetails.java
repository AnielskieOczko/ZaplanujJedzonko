package pl.coderslab.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dto.PlanDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet("/app/plan/details")
public class AdminPlanDetails extends HttpServlet {
    public static final Logger logger = LogManager.getLogger(AdminPlanDetails.class);
    public static final PlanDao planDao = new PlanDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int planId = Integer.parseInt(req.getParameter("id"));

        PlanDto planDto = planDao.getPlanForAdmin(planId);
        logger.info(planDto);


    }
}
