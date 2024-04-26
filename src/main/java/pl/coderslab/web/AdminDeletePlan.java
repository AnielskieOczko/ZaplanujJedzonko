package pl.coderslab.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.dao.PlanDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/plan/delete")
public class AdminDeletePlan extends HttpServlet {
    public static final PlanDao planDao = new PlanDao();
    public static final Logger logger = LogManager.getLogger(AdminDeletePlan.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int planId = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("planId", planId);
        getServletContext().getRequestDispatcher("/app-plan-delete-confirmation.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int planId = Integer.parseInt(req.getParameter("id"));
        String isPlanToDelete = req.getParameter("delete");

        if (isPlanToDelete.equals("true")) {
            try {
                planDao.delete(planId);
            } catch (Exception e) {
                resp.sendRedirect("/ops");
            }
        }

        resp.sendRedirect("/app/plan/list");
    }
}
