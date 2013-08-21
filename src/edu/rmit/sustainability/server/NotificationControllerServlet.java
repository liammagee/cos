package edu.rmit.sustainability.server;

import edu.rmit.sustainability.messaging.Message;
import edu.rmit.sustainability.messaging.MessageBusFactory;
import edu.rmit.sustainability.messaging.MessagingException;
import edu.rmit.sustainability.messaging.feedback.Feedback;
import edu.rmit.sustainability.messaging.feedback.Notification;
import edu.rmit.sustainability.messaging.feedback.SimpleSuggestion;
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
public class NotificationControllerServlet extends HttpServlet {

    public static String empirePropertyPath = "";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");

        Notification[] notifications = null;

        if (user != null && project != null) {
            try {
                Queue messages = MessageBusFactory.createMessageBus().retrieveMessage(user, project);
                if (messages != null) {
                    notifications = new Notification[messages.size()];
                    Iterator i = messages.iterator();
                    int counter = 0;
                    while (i.hasNext()) {
                        Message m = (Message) i.next();
                        if (m != null) {
                            Object o = m.getMessageContents();
                            if (o instanceof Notification) {
                                notifications[counter] = (Notification) o;
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

        req.setAttribute("notifications", notifications);

        getServletContext().getRequestDispatcher("/fragments/notifications.jsp").forward(req, resp);
    }

}
