package edu.rmit.sustainability.server;

import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import edu.rmit.sustainability.model.*;
import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelateIssueControllerServlet extends HttpServlet {

    private final DomainExtractor domainExtractor = new DomainExtractor();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        // TODO: Needs to be externalised into configuration

        if ("overview".equals(action)) {
            relateIssues(req, resp);
        } else if ("edit".equals(action)) {
            editIssue(req, resp);
        } else if ("assign".equals(action)) {
            assignIndicatorToIssue(req, resp);
        } else if ("update".equals(action)) {
            updateIssue(req, resp);
        } else if ("new".equals(action)) {
            newIssue(req, resp);
        } else if ("delete".equals(action)) {
            deleteIssue(req, resp);
        }
    }


    private void relateIssues(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/fragments/issue/relate_issues.jsp").forward(req, resp);
    }

    private void newIssue(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CriticalIssue criticalIssue = new CriticalIssue();
        criticalIssue.setName("New Critical Issue");
        criticalIssue.setDescription("[Describe your issue here]");
        req.getSession().setAttribute("issue", criticalIssue);

        try {
            req.setAttribute("domains", DomainExtractor.getDomains());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        getServletContext().getRequestDispatcher("/fragments/issue/edit_issue.jsp").forward(req, resp);
    }


    private void editIssue(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String issueID = req.getParameter("issueID");
        if (issueID != null) {
            // Using Empire instead
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
            CriticalIssue criticalIssue = em.find(CriticalIssue.class, URI.create(issueID));
            if (criticalIssue != null)
                req.getSession().setAttribute("issue", criticalIssue);

        }
        try {
            req.setAttribute("domains", DomainExtractor.getDomains());
        } catch (Exception e) {
        }

        getServletContext().getRequestDispatcher("/fragments/issue/edit_issue.jsp").forward(req, resp);
    }

    private void updateIssue(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        CriticalIssue criticalIssue = (CriticalIssue) req.getSession().getAttribute("issue");
        Project project = (Project) req.getSession().getAttribute("project");

        try {
            // Persist...
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);

            Map<String, String[]> parameters = new HashMap<String, String[]>(req.getParameterMap());
            String[] subdomainIDs = parameters.remove("subdomainId");
            List<Subdomain> subdomains = new ArrayList<Subdomain>();
            if (subdomainIDs != null && subdomainIDs.length > 0) {
                for (int i = 0; i < subdomainIDs.length; i++) {
                    String subdomainID = subdomainIDs[i];
                    Subdomain subdomain = em.find(Subdomain.class, URI.create(subdomainID));
                    subdomains.add(subdomain);
                }
            }
            criticalIssue.setSubdomains(subdomains);

            BeanUtils.populate(criticalIssue, parameters);


            // Use 'merge' when updating an object
            if (criticalIssue.getRdfId() == null) {
//                em.persist(criticalIssue);
                criticalIssue.setCreator(user);
                criticalIssue.setProject(project);
                List<CriticalIssue> issues = project.getCriticalIssues();
                issues.add(criticalIssue);
                // Save the project - issues should save as well
                em.merge(project);
            } else {
                em.merge(criticalIssue);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        getServletContext().getRequestDispatcher("/workingProject?action=open&projectID=" + project.getRdfId().value()).forward(req, resp);
    }

    private void deleteIssue(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CriticalIssue criticalIssue = (CriticalIssue) req.getSession().getAttribute("issue");
        Project project = (Project) req.getSession().getAttribute("project");

        try {
            // Persist...
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
            em.remove(criticalIssue);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        getServletContext().getRequestDispatcher("/workingProject?action=open&projectID=" + project.getRdfId().value()).forward(req, resp);
    }

    private void assignIndicatorToIssue(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CriticalIssue criticalIssue = (CriticalIssue) req.getSession().getAttribute("issue");
        String issueID = req.getParameter("issueID");
        String indicatorID = req.getParameter("indicatorID");
        EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
        if (criticalIssue == null && issueID != null) {
            criticalIssue = em.find(CriticalIssue.class, URI.create(issueID));
            if (criticalIssue != null)
                req.getSession().setAttribute("issue", criticalIssue);

        }
        if (criticalIssue != null && indicatorID != null) {
            Indicator indicator = em.find(Indicator.class, URI.create(indicatorID));
            if (indicator != null) {
                List<Indicator> criticalIssueIndicators = criticalIssue.getIndicators();
                if (!criticalIssueIndicators.contains(indicator)) {
                    criticalIssueIndicators.add(indicator);
                    criticalIssue.setIndicators(criticalIssueIndicators);
                    em.merge(criticalIssue);
                }
            }
        }

        try {
            req.setAttribute("domains", DomainExtractor.getDomains());
        } catch (Exception e) {
        }

        getServletContext().getRequestDispatcher("/fragments/issue/edit_issue.jsp").forward(req, resp);
    }
}
