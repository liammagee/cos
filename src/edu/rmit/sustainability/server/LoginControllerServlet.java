package edu.rmit.sustainability.server;

import com.clarkparsia.empire.impl.RdfQuery;
import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LoginControllerServlet extends HttpServlet {

    public static String empirePropertyPath = "";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: set this somewhere else
        empirePropertyPath = getServletContext().getRealPath("/").toLowerCase() + "WEB-INF/examples.empire.config.properties";

        String username = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
            if (em != null) {
                Query query = em.createQuery(
                        "WHERE { ?result a <http://circlesofsustainability.org/ontology#User> . " +
                                " ?result <http://circlesofsustainability.org/ontology#hasUsername> '" + username + "' . " +
                                " ?result <http://circlesofsustainability.org/ontology#hasPassword> '" + password + "' . " +
                                "}");
                // this query should return instances of type Book
                query.setHint(RdfQuery.HINT_ENTITY_CLASS, User.class);

                User user = (User) query.getSingleResult();

                if (user != null) {
                    // Add the user to the current HTTP session
                    req.getSession().setAttribute("user", user);

                    // Checks if the user is a collaborator as well
                    // TODO: Should projects in which the current user is a creator be separated from those s/he collaborates on?
                    query = em.createQuery("WHERE {?result a <http://circlesofsustainability.org/ontology#Project> . " +
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

                    getServletContext().getRequestDispatcher("/authenticated.jsp").forward(req, resp);
                    return;
                } else {
                    // If we get to here, there is something wrong with the login...
                    getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }
        // If we get to here, there is something wrong with the login...
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
