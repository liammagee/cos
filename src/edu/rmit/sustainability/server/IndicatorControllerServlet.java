package edu.rmit.sustainability.server;

import com.clarkparsia.empire.impl.RdfQuery;
import edu.rmit.sustainability.agentinterface.AgentInterface;
import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import edu.rmit.sustainability.data.query.PrivateIndicatorQuery;
import edu.rmit.sustainability.data.query.PublicIndicatorSetQuery;
import edu.rmit.sustainability.model.*;
import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.*;

public class IndicatorControllerServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        // TODO: Needs to be externalised into configuration

        if ("list".equals(action)) {
            listIndicators(req, resp);
        } else if ("edit".equals(action)) {
            editIndicator(req, resp);
        } else if ("show".equals(action)) {
            showIndicator(req, resp);
        } else if ("update".equals(action)) {
            updateIndicator(req, resp);
        } else if ("new".equals(action)) {
            newIndicator(req, resp);
        } else if ("delete".equals(action)) {
            deleteIndicator(req, resp);
        }
    }

    private void listIndicators(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");

        // Notify the Agent System - should add issues for us to check
        CriticalIssue criticalIssue = ((CriticalIssue) req.getSession().getAttribute("issue"));
        if (criticalIssue != null) {
            AgentInterface.getInstance().sendData(project, user, new IndicatorSuggestionRequest(criticalIssue));
        }


        EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
        List<IndicatorSet> indicatorSets = new PublicIndicatorSetQuery(em).doQuery(project, user);
        req.setAttribute("indicatorSets", indicatorSets);


        // Some fancy syntax to select only indicators bound to no set...
        if (project != null) {
            List<Indicator> indicators = new PrivateIndicatorQuery(em).doQuery(project, user);
            req.setAttribute("indicators", indicators);
        }

        getServletContext().getRequestDispatcher("/fragments/indicator/indicator_listing.jsp").forward(req, resp);
    }


    private void newIndicator(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");
        String indicatorName = "New Indicator";
        if (project != null) {
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
            List<Indicator> indicators = new PrivateIndicatorQuery(em).doQuery(project, user);
            if (indicators != null && indicators.size() > 1) {
                indicatorName += " " + (indicators.size() + 1);
            }
        }
        Indicator indicator = new Indicator();
        indicator.setName(indicatorName);
        indicator.setDescription("[Describe your indicator here]");
        indicator.setIdentifyingCode("");
        indicator.setSource("");
        indicator.setUnitOfMeasure("");
        indicator.setCurrentProject(project);
        indicator.setCurrentUser(user);
        req.getSession().setAttribute("indicator", indicator);
        try {
            req.setAttribute("domains", DomainExtractor.getDomains());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        getServletContext().getRequestDispatcher("/fragments/indicator/edit_indicator.jsp").forward(req, resp);
    }


    private void editIndicator(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");
        String indicatorID = req.getParameter("indicatorID");

        if (indicatorID != null) {
            // Using Empire instead
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
            Indicator indicator = em.find(Indicator.class, URI.create(indicatorID));
            if (indicator != null) {
                indicator.setCurrentProject(project);
                indicator.setCurrentUser(user);
                req.getSession().setAttribute("indicator", indicator);
            }
        }

        try {
            req.setAttribute("domains", DomainExtractor.getDomains());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        getServletContext().getRequestDispatcher("/fragments/indicator/edit_indicator.jsp").forward(req, resp);
    }

    private void showIndicator(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");
        String indicatorID = req.getParameter("indicatorID");

        if (indicatorID != null) {
            // Using Empire instead
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
            Indicator indicator = em.find(Indicator.class, URI.create(indicatorID));
            if (indicator != null) {
                indicator.setCurrentProject(project);
                indicator.setCurrentUser(user);
                req.getSession().setAttribute("indicator", indicator);
            }
        }

        getServletContext().getRequestDispatcher("/fragments/indicator/show_indicator.jsp").forward(req, resp);
    }

    private void updateIndicator(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");
        Indicator indicator = (Indicator) req.getSession().getAttribute("indicator");
        CriticalIssue criticalIssue = (CriticalIssue) req.getSession().getAttribute("issue");

        try {
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);

            Map<String, String[]> parameters = new HashMap<String, String[]>(req.getParameterMap());
            String[] subdomainIDs = parameters.remove("subdomainId");
            /*
            if (subdomainIDs != null && subdomainIDs.length > 0 && subdomainIDs[0].length() > 0) {
                Subdomain subdomain = em.find(Subdomain.class, URI.create(subdomainIDs[0]));
                indicator.setSubdomain(subdomain);
            }
            */
            List<Subdomain> subdomains = new ArrayList<Subdomain>();
            if (subdomainIDs != null && subdomainIDs.length > 0) {
                for (int i = 0; i < subdomainIDs.length; i++) {
                    String subdomainID = subdomainIDs[i];
                    Subdomain subdomain = em.find(Subdomain.class, URI.create(subdomainID));
                    subdomains.add(subdomain);
                }
            }
            indicator.setSubdomains(subdomains);


//            parameters.put("target.value", new String[]{"2.0"});
            BeanUtils.populate(indicator, parameters);
//            indicator.getTarget().setValue("3.0");


            // Use 'merge' when updating an object
            if (indicator.getRdfId() == null) {
                indicator.setCreator(user);
                indicator.setProject(project);
                if (criticalIssue != null && criticalIssue.getName() != null) {
                    List<Indicator> indicators = criticalIssue.getIndicators();
                    indicators.add(indicator);
                    // Save the project - issues should save as well
                    em.merge(criticalIssue);
                } else {
                    em.persist(indicator);
                }
            } else {
                em.merge(indicator);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if (criticalIssue != null && criticalIssue.getName() != null)
            getServletContext().getRequestDispatcher("/criticalIssue?action=edit&issueID=" + criticalIssue.getId()).forward(req, resp);
        else
            getServletContext().getRequestDispatcher("/indicator?action=list").forward(req, resp);
    }

    private void deleteIndicator(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");
        String indicatorID = req.getParameter("indicatorID");
        CriticalIssue criticalIssue = (CriticalIssue) req.getSession().getAttribute("issue");

        try {
            // Persist...
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
            Indicator retrievedIndicator = em.find(Indicator.class, URI.create(indicatorID));
            if (criticalIssue != null && criticalIssue.getName() != null) {
                criticalIssue.getIndicators().remove(retrievedIndicator);
                em.merge(criticalIssue);
            }
            em.remove(retrievedIndicator);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if (criticalIssue != null && criticalIssue.getName() != null)
            getServletContext().getRequestDispatcher("/criticalIssue?action=edit&issueID=" + criticalIssue.getId()).forward(req, resp);
        else
            getServletContext().getRequestDispatcher("/indicator?action=list").forward(req, resp);
    }
}
