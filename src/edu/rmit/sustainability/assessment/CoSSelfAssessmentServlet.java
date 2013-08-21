package edu.rmit.sustainability.assessment;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Liam
 * Date: 28/01/2010
 * Time: 5:42:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class CoSSelfAssessmentServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String method = req.getParameter("method");
        String step = req.getParameter("step");
        String id = req.getParameter("id");
        CoSSelfAssessment entry = null;
        if (id != null) {
            entry = CoSSelfAssessmentUtils.retrieve(id);
            req.setAttribute("entry", entry);
            req.setAttribute("id", entry.getId());
        }
        if (method != null && id != null && method.equals("delete")) {
            CoSSelfAssessmentUtils.delete(id);
            resp.sendRedirect("/assessment/index.jsp");
        } else if (step == null || step.equals("1")) {
            forward(req, resp, "/assessment/step1.jsp");
        } else if (step.equals("2")) {
            String orgName = req.getParameter("organisationName");
            if (orgName != null) {
                if (id == null) {
                    entry = CoSSelfAssessmentUtils.insertNew(orgName);
                } else {
                    entry = CoSSelfAssessmentUtils.updateName(id, orgName);
                }
            }
            req.setAttribute("entry", entry);
            req.setAttribute("id", entry.getId());
            forward(req, resp, "/assessment/step2.jsp");
        } else if (step.equals("3")) {
            String q1 = req.getParameter("q1");
            String q2 = req.getParameter("q2");
            String q3 = req.getParameter("q3");
            String q4 = req.getParameter("q4");
            if (q1 != null && q2 != null && q3 != null && q4 != null) {
                entry = CoSSelfAssessmentUtils.updateBackgroundConsiderations(id, q1, q2, q3, q4);
            }
            req.setAttribute("entry", entry);
            req.setAttribute("id", id);
            req.setAttribute("q", new Integer(1));
            forward(req, resp, "/assessment/step3.jsp");
        } else if (step.equals("4")) {
            int q = 0;
            try {
                q = Integer.parseInt(req.getParameter("q"));
            } catch (NumberFormatException e) {
                q = 4;
            }
            String d1 = req.getParameter("d1");
            String d2 = req.getParameter("d2");
            String d3 = req.getParameter("d3");
            String d4 = req.getParameter("d4");
            CoSSelfAssessmentUtils.updateStageOneQuestions(id, q, d1, d2, d3, d4);
            req.setAttribute("q", new Integer(q + 1));
            if (q < 4)
                forward(req, resp, "/assessment/step3.jsp");
            else
                forward(req, resp, "/assessment/step4.jsp");
        } else if (step.equals("5")) {
            forward(req, resp, "/assessment/step5.jsp");
        }
    }

    public void forward(HttpServletRequest req, HttpServletResponse resp, String name) {
        try {
            getServletConfig().getServletContext().getRequestDispatcher(name).forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}