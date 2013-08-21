package edu.rmit.sustainability.server;

import edu.rmit.sustainability.messaging.Message;
import edu.rmit.sustainability.messaging.MessageBusFactory;
import edu.rmit.sustainability.messaging.MessagingException;
import edu.rmit.sustainability.messaging.feedback.Feedback;
import edu.rmit.sustainability.messaging.feedback.SimpleSuggestion;
import edu.rmit.sustainability.messaging.feedback.Suggestion;
import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Queue;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SuggestionControllerServlet extends HttpServlet {

    public static String empirePropertyPath = "";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");

        Suggestion[] suggestions = null;

        if (user != null && project != null) {
            try {
                Queue messages = MessageBusFactory.createMessageBus().retrieveMessage(user, project);
                if (messages != null) {
                    suggestions = new Suggestion[messages.size()];
                    Iterator i = messages.iterator();
                    int counter = 0;
                    while (i.hasNext()) {
                        Message m = (Message) i.next();
                        if (m != null) {
                            Object o = m.getMessageContents();
                            if (o instanceof Suggestion) {
                                suggestions[counter] = (Suggestion) o;
                                m.incrementNumberOfTimesRetrieved();
                                if (m.getTimesRetrieved() > 10)
                                    MessageBusFactory.createMessageBus().clearMessage(user, project, m);
                            }
                            counter++;
                        }
                    }
                }
//                MessageBusFactory.createMessageBus().clearMessages(user, project);
            } catch (MessagingException me) {
                System.out.println(me.getMessage());
            }
        }

        if (suggestions == null) {
            suggestions = new Suggestion[1];
            suggestions[0] = new Suggestion("No suggestions for the moment...");
        }
        req.setAttribute("suggestions", suggestions);

        getServletContext().getRequestDispatcher("/fragments/suggestions.jsp").forward(req, resp);
    }

}
