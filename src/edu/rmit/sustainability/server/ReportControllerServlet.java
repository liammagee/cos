package edu.rmit.sustainability.server;

import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;
import edu.rmit.sustainability.model.assess.Assessment;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

public class ReportControllerServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        // TODO: Needs to be externalised into configuration

        if (action == null || "list".equals(action)) {
            listReports(req, resp);
        } else if ("view".equals(action)) {
            viewReport(req, resp);
        } else if ("open".equals(action)) {
            openReport(req, resp);
        } else if ("edit".equals(action)) {
            editReport(req, resp);
        } else if ("update".equals(action)) {
            updateReport(req, resp);
        } else if ("new".equals(action)) {
            newReport(req, resp);
        } else if ("delete".equals(action)) {
            deleteReport(req, resp);
        }
    }

    private void listReports(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
//        Query query = em.createQuery(
//                "WHERE { ?result a <http://circlesofsustainability.org/ontology#Project> . " + "}");
//
//        // this query should return instances of type Project
//        query.setHint(RdfQuery.HINT_ENTITY_CLASS, Project.class);
//
//        List aResults = query.getResultList();
//
//        List<Project> projects = new ArrayList<Project>();
//        for (Iterator iterator = aResults.iterator(); iterator.hasNext(); ) {
//            projects.add((Project) iterator.next());
//        }
//        req.setAttribute("projects", projects);
        getServletContext().getRequestDispatcher("/fragments/report/report_listing.jsp").forward(req, resp);
    }

    private void viewReport(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");
        String projectID = project.getId().toString();

        if (projectID != null) {
            // If there is an existing project in session, clear any messages pertaining to it.
//            clearProjectMessages(req);

            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
            project = em.find(Project.class, URI.create(projectID));

            if (project != null) {
                project.setCurrentUser(user);
                project.setCurrentProject(project);
                req.getSession().setAttribute("project", project);
            }
            Assessment assessment = new Assessment();
            List<Assessment> assessmentList = project.getAssessments();
            int size = assessmentList.size();
            if (size > 0)
                assessment = project.getAssessments().get(size - 1);
            req.getSession().setAttribute("assessment", assessment);

            try {
                req.setAttribute("domains", DomainExtractor.getDomains());
            } catch (Exception e) {
            }

            getServletContext().getRequestDispatcher("/fragments/report/view_report.jsp").forward(req, resp);
        }
    }

    private void openReport(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectID = req.getParameter("projectID");
//
//        if (projectID != null) {
//            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
//            Project project = em.find(Project.class, URI.create(projectID));
//
//            if (project != null)
//                req.getSession().setAttribute("project", project);
//        }
//
        getServletContext().getRequestDispatcher("/fragments/reports/edit_report.jsp").forward(req, resp);
    }

    private void newReport(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Project project = new Project("New Project", "[Describe your project here]", "[What is the general issue the project is trying to solve?]", "[What is the goal of the project?]");
//        req.getSession().setAttribute("project", project);
        getServletContext().getRequestDispatcher("/fragments/reports/edit_report.jsp").forward(req, resp);
    }


    private void editReport(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        getServletContext().getRequestDispatcher("/fragments/edit_project.jsp").forward(req, resp);
    }

    private void updateReport(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        User user = (User)req.getSession().getAttribute("user");
//        Project project = (Project)req.getSession().getAttribute("project");
//
//        try {
//            BeanUtils.populate(project, req.getParameterMap());
//
//            // Persist...
//            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
//
//            // Use 'merge' when updating an object
//            if (project.getRdfId() == null) {
//                project.setCreator(user);
//                em.persist(project);
//            }
//            else
//                em.merge(project);
//
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//        getServletContext().getRequestDispatcher("/fragments/working_project.jsp").forward(req, resp);
    }

    private void deleteReport(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Project project = (Project)req.getSession().getAttribute("project");
//
//        try {
//            BeanUtils.populate(project, req.getParameterMap());
//
//            // Persist...
//            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
//
//            // Use 'merge' when updating an object
////            em.persist(project);
//            em.remove(project);
//
//            Query query = em.createQuery("WHERE {?result a http://circlesofsustainability.org/ontology#Project}");
//
//            // this query should return instances of type Book
//            query.setHint(RdfQuery.HINT_ENTITY_CLASS, Project.class);
//
//            List aResults = query.getResultList();
//            List<Project> projects = new ArrayList<Project>();
//            for (Iterator iterator = aResults.iterator(); iterator.hasNext(); ) {
//                projects.add((Project) iterator.next());
//            }
//            req.setAttribute("projects", projects);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//
//        getServletContext().getRequestDispatcher("/fragments/project_listing.jsp").forward(req, resp);
    }
}
