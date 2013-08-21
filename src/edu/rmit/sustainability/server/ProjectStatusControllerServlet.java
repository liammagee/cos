package edu.rmit.sustainability.server;

import edu.rmit.sustainability.agentinterface.AgentInterface;
import edu.rmit.sustainability.messaging.Message;
import edu.rmit.sustainability.messaging.MessageBusFactory;
import edu.rmit.sustainability.messaging.MessagingException;
import edu.rmit.sustainability.messaging.feedback.Feedback;
import edu.rmit.sustainability.messaging.feedback.SimpleSuggestion;
import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Queue;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ProjectStatusControllerServlet extends HttpServlet {

    public static String empirePropertyPath = "";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");

        // Update the agent interface, so the status is updated
        AgentInterface.getInstance().sendData(project, user, project);

        JSONObject obj = new JSONObject();
        obj.put("project", project);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Writer writer = null;
        try {
            writer = resp.getWriter();
            obj.writeJSONString(writer);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            writer.close();
        }
    }
}
