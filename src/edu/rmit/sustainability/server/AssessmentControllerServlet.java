package edu.rmit.sustainability.server;

import edu.rmit.sustainability.agentinterface.AgentInterface;
import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import edu.rmit.sustainability.model.*;
import edu.rmit.sustainability.model.assess.Assessment;
import edu.rmit.sustainability.model.assess.AssessmentValue;
import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.*;

public class AssessmentControllerServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null || "list".equals(action)) {
            listAssessments(req, resp);
        } else if ("open".equals(action)) {
            openAssessment(req, resp);
        } else if ("edit".equals(action)) {
            editAssessment(req, resp);
        } else if ("update".equals(action)) {
            updateAssessment(req, resp);
        } else if ("updateValue".equals(action)) {
            updateAssessmentValue(req, resp);
        } else if ("new".equals(action)) {
            newAssessment(req, resp);
        } else if ("delete".equals(action)) {
            deleteAssessment(req, resp);
        }
    }

    private void listAssessments(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/fragments/assess/assessment_listing.jsp").forward(req, resp);
    }

    private void openAssessment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("domains", DomainExtractor.getDomains());
        } catch (Exception e) {
        }

        getServletContext().getRequestDispatcher("/fragments/assess/edit_assessment.jsp").forward(req, resp);
    }

    private void newAssessment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");
        Assessment assessment = new Assessment();
        assessment.setCreatedAt(new Date(System.currentTimeMillis()));
        assessment.setCurrentUser(user);
        assessment.setCurrentProject(project);
        List<AssessmentValue> assessmentValues = new ArrayList<AssessmentValue>();
        List<Domain> domains = DomainExtractor.getDomains();
        for (Domain domain : domains) {
            List<Subdomain> subdomains = domain.getSubdomains();
            for (Subdomain subdomain : subdomains) {
                AssessmentValue assessmentValue = new AssessmentValue();
                assessmentValue.setSubdomain(subdomain);
                assessmentValue.setValue(1);
                assessmentValues.add(assessmentValue);
            }
        }
        assessment.setValues(assessmentValues);

        try {
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
            em.persist(assessment);
            List<Assessment> assessments = project.getAssessments();
            assessments.add(assessment);
            // Save the project - issues should save as well
            em.merge(project);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        req.getSession().setAttribute("assessment", assessment);

        try {
            req.setAttribute("domains", DomainExtractor.getDomains());
        } catch (Exception e) {
        }

        getServletContext().getRequestDispatcher("/fragments/assess/edit_assessment.jsp").forward(req, resp);
    }


    private void editAssessment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");
        String assessmentID = req.getParameter("assessmentID");
        if (assessmentID != null) {
            // Using Empire instead
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
            Assessment assessment = em.find(Assessment.class, URI.create(assessmentID));
            if (assessment != null) {
                assessment.setCurrentUser(user);
                assessment.setCurrentProject(project);
                req.getSession().setAttribute("assessment", assessment);
                em.merge(assessment);
//                AgentInterface.getInstance().sendData(project, user, assessment);
            }
        }
        try {
            req.setAttribute("domains", DomainExtractor.getDomains());
        } catch (Exception e) {
        }

        getServletContext().getRequestDispatcher("/fragments/assess/edit_assessment.jsp").forward(req, resp);
//        getServletContext().getRequestDispatcher("/fragments/assess/save_assessment.jsp").forward(req, resp);
    }

    private void updateAssessment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Assessment assessment = (Assessment) req.getSession().getAttribute("assessment");
        Project project = (Project) req.getSession().getAttribute("project");

        try {
            // Persist...
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);

            Map<String, String[]> parameters = new HashMap<String, String[]>(req.getParameterMap());
            BeanUtils.populate(assessment, parameters);

            // Use 'merge' when updating an object
            if (assessment.getRdfId() == null) {
                em.persist(assessment);
                List<Assessment> assessments = project.getAssessments();
                assessments.add(assessment);
                // Save the project - issues should save as well
                em.merge(project);
            } else {
                em.merge(assessment);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

//        getServletContext().getRequestDispatcher("/fragments/assess/save_assessment.jsp").forward(req, resp);
        getServletContext().getRequestDispatcher("/assessment?action=edit&assessmentID=" + assessment.getId()).forward(req, resp);
    }

    private void updateAssessmentValue(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Assessment assessment = (Assessment) req.getSession().getAttribute("assessment");
        Project project = (Project) req.getSession().getAttribute("project");
        String domainId = (String) req.getParameter("domainId");
        String subdomainId = (String) req.getParameter("subdomainId");
        String value = (String) req.getParameter("value");

        try {
            // Persist...
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);

            // Calculate the subdomain ID
            try {
                int subdomainIndex = Integer.parseInt(subdomainId);
//                int subdomainIndex = Integer.parseInt(domainId) * 4 + Integer.parseInt(subdomainId);
                AssessmentValue assessmentValue = assessment.getValues().get(subdomainIndex);
                assessmentValue.setValue(Integer.parseInt(value));
                em.merge(assessmentValue);
            }
            catch (NumberFormatException nfe) {}

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            req.setAttribute("domains", DomainExtractor.getDomains());
        } catch (Exception e) {
        }

        req.getSession().setAttribute("assessment", assessment);
        getServletContext().getRequestDispatcher("/fragments/assess/edit_assessment.jsp").forward(req, resp);
//        getServletContext().getRequestDispatcher("/assessment?action=edit&assessmentID=" + assessment.getId()).forward(req, resp);
    }

    private void deleteAssessment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");
        String assessmentID = req.getParameter("assessmentID");

        try {
            // Persist...
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
            Assessment retrievedAssessment = em.find(Assessment.class, URI.create(assessmentID));
            int index = -1;
            for (int i = 0 ; i < project.getAssessments().size(); i++) {
                Assessment assessment = (Assessment)project.getAssessments().get(i);
                if (assessment.getId().equals(assessmentID)) {
                    index = i;
                }
            }
            if (index > -1)
                project.getAssessments().remove(index);

            em.merge(project);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        getServletContext().getRequestDispatcher("/assessments?action=list").forward(req, resp);
    }
}
