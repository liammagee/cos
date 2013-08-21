package edu.rmit.sustainability.server;

import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WelcomeControllerServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        welcome(req, resp);
    }

    private void welcome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");

        if (user != null)
            getServletContext().getRequestDispatcher("/authenticated.jsp").forward(req, resp);
        else
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }

}
