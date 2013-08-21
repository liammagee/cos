package edu.rmit.sustainability.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuestionnaireControllerServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        // TODO: Needs to be externalised into configuration

        if (action == null || "list".equals(action)) {
            listQuestionnaires(req, resp);
        } else if ("open".equals(action)) {
            openQuestionnaires(req, resp);
        } else if ("edit".equals(action)) {
            editQuestionnaires(req, resp);
        } else if ("update".equals(action)) {
            updateQuestionnaires(req, resp);
        } else if ("new".equals(action)) {
            newQuestionnaires(req, resp);
        } else if ("delete".equals(action)) {
            deleteQuestionnaires(req, resp);
        }
    }

    private void listQuestionnaires(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        getServletContext().getRequestDispatcher("/fragments/questionnaire/questionnaire_listing.jsp").forward(req, resp);
    }

    private void openQuestionnaires(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String projectID = req.getParameter("projectID");
//
//        if (projectID != null) {
//            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
//            Project project = em.find(Project.class, URI.create(projectID));
//
//            if (project != null)
//                req.getSession().setAttribute("project", project);
//        }
//
        getServletContext().getRequestDispatcher("/fragments/questionnaire/edit_questionnaire.jsp").forward(req, resp);
    }

    private void newQuestionnaires(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Project project = new Project("New Project", "[Describe your project here]", "[What is the general issue the project is trying to solve?]", "[What is the goal of the project?]");
//        req.getSession().setAttribute("project", project);
        getServletContext().getRequestDispatcher("/fragments/questionnaire/edit_questionnaire.jsp").forward(req, resp);
    }


    private void editQuestionnaires(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        getServletContext().getRequestDispatcher("/fragments/edit_project.jsp").forward(req, resp);
    }

    private void updateQuestionnaires(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

    private void deleteQuestionnaires(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
