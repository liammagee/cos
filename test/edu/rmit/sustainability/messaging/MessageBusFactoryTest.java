package edu.rmit.sustainability.messaging;


import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import java.util.Queue;
import edu.rmit.sustainability.model.*;
import edu.rmit.sustainability.messaging.*;


public class MessageBusFactoryTest extends TestCase {

	private User user;
	private Project project;
	
	@Before
	public void setUp() throws Exception {
		user = new User("sustain", "able");
		project = new Project();
		project.setProjectName("New project");
		project.setProjectDescription("Some kind of new sustainability project.");
	}
	
	public void testCreateMessageBus() {
		assertNotNull(MessageBusFactory.createMessageBus());
	}
	
	public void testSendAndReceiveMessages() {
		Message message = new Message("This is a message");
		try {
			MessageBusFactory.createMessageBus().sendMessage(user, project, message);
		}
		catch (MessagingException me) {
			assertFalse("Shouldn't have thrown an exception", true);
		}
		try {
			Queue queue = MessageBusFactory.createMessageBus().retrieveMessage(user, project);
			Message receivedMessage = (Message)queue.peek();
			assertEquals(message, receivedMessage);
		}
		catch (MessagingException me) {
			assertFalse("Shouldn't have thrown an exception "  + me.getMessage(), true);
		}
		catch (NullPointerException npe) {
			assertFalse("Shouldn't have thrown an exception " + npe.getMessage(), true);
		}
		assertNotNull(MessageBusFactory.createMessageBus());
	}

	@After
	public void tearDown() throws Exception {
	}

}
