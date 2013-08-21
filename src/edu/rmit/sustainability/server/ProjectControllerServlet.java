package edu.rmit.sustainability.server;

import com.clarkparsia.empire.impl.RdfQuery;
import edu.rmit.sustainability.agentinterface.AgentInterface;
import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import edu.rmit.sustainability.messaging.MessageBusFactory;
import edu.rmit.sustainability.messaging.MessagingException;
import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ProjectControllerServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        // TODO: Needs to be externalised into configuration

        if (action == null || "list".equals(action)) {
            listProjects(req, resp);
        } else if ("open".equals(action)) {
            openProject(req, resp);
        } else if ("edit".equals(action)) {
            editProject(req, resp);
        } else if ("update".equals(action)) {
            updateProject(req, resp);
        } else if ("new".equals(action)) {
            newProject(req, resp);
        } else if ("delete".equals(action)) {
            deleteProject(req, resp);
        }
    }

    private void listProjects(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);

        // Checks if the user is a collaborator as well
        // TODO: Should projects in which the current user is a creator be separated from those s/he collaborates on?
        Query query = em.createQuery("WHERE {?result a <http://circlesofsustainability.org/ontology#Project> . " +
                " { ?result <http://circlesofsustainability.org/ontology#createdBy> <" + user.getId() + ">  } " +
                " UNION " +
                " { ?result <http://circlesofsustainability.org/ontology#hasCollaborator> <" + user.getId() + "> }  " +
                "}");

        // this query should return instances of type Project
        query.setHint(RdfQuery.HINT_ENTITY_CLASS, Project.class);

        List aResults = query.getResultList();

        List<Project> projects = new ArrayList<Project>();
        for (Iterator iterator = aResults.iterator(); iterator.hasNext(); ) {
            projects.add((Project) iterator.next());
        }
        req.setAttribute("projects", projects);
        getServletContext().getRequestDispatcher("/fragments/project_listing.jsp").forward(req, resp);
    }

    private void openProject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectID = req.getParameter("projectID");
        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");

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
            getServletContext().getRequestDispatcher("/fragments/working_project.jsp").forward(req, resp);
        } else if (project != null) {
            getServletContext().getRequestDispatcher("/fragments/working_project.jsp").forward(req, resp);
        } else {
            req.setAttribute("notice", "Please select a project first");
            getServletContext().getRequestDispatcher("/workingProject?action=list").forward(req, resp);
        }
    }

    private void newProject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Project project = new Project("New Project", "[Describe your project here]", "[What is the general issue the project is trying to solve?]", "[What is the goal of the project?]");
        project.createdOn = new Date();
        req.getSession().setAttribute("project", project);
        getServletContext().getRequestDispatcher("/fragments/edit_project.jsp").forward(req, resp);
    }


    private void editProject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        clearProjectMessages(req);
        getServletContext().getRequestDispatcher("/fragments/edit_project.jsp").forward(req, resp);
    }

    private void clearProjectMessages(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");
        // If there is an existing project in session, clear any messages pertaining to it.
        if (project != null && user != null) {
            try {
                MessageBusFactory.createMessageBus().clearMessages(user, project);
            } catch (MessagingException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    private void updateProject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");

        try {
            BeanUtils.populate(project, req.getParameterMap());

            // Persist...
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);

            // Use 'merge' when updating an object
            if (project.getRdfId() == null) {
                project.setCreator(user);
                em.persist(project);
            }
            else
            	em.merge(project);

        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        getServletContext().getRequestDispatcher("/fragments/working_project.jsp").forward(req, resp);
    }

    private void deleteProject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String projectID = (String) req.getParameter("projectID");

        try {
            // Persist...
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);

            Project project = em.find(Project.class, URI.create(projectID));
            em.remove(project);

            Query query = em.createQuery("WHERE {?result a <http://circlesofsustainability.org/ontology#Project> . " +
                    " { ?result <http://circlesofsustainability.org/ontology#createdBy> <" + user.getId() + ">  } " +
                    " UNION " +
                    " { ?result <http://circlesofsustainability.org/ontology#hasCollaborator> <" + user.getId() + "> }  " +
                    "}");

            // this query should return instances of type Book
            query.setHint(RdfQuery.HINT_ENTITY_CLASS, Project.class);

            List aResults = query.getResultList();
            List<Project> projects = new ArrayList<Project>();
            for (Iterator iterator = aResults.iterator(); iterator.hasNext(); ) {
                projects.add((Project) iterator.next());
            }
            req.setAttribute("projects", projects);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        getServletContext().getRequestDispatcher("/fragments/project_listing.jsp").forward(req, resp);
    }
}
