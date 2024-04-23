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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/app/plan/list")
public class AdminPlanList extends HttpServlet {
    public static final Logger logger = LogManager.getLogger(AdminPlanList.class);
    public static final PlanDao planDao = new PlanDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int adminId = (int) session.getAttribute("adminId");
        List<PlanDto> adminPlans = planDao.getAllPlansForAdmin(adminId);
        session.setAttribute("adminPlans", adminPlans);
        getServletContext().getRequestDispatcher("/app-schedule-list.jsp").forward(req, resp);
    }
}
