package com.PortfolioManager.businessGUI.accountManagement;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

/**
 * 
 * @author Ahmed
 */
public class EmailNotifier {
	
	protected final String SMTPSERVER="smtp.topnet.tn";
	private String to;
	private String from;
	private String subject;
	private String body;

	public EmailNotifier() {
		// TODO Auto-generated constructor stub
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void send() {
		try {
			// Determines the current system properties.
			Properties props = System.getProperties();
			// -- Attaching to default Session, or we could start a new one --
			props.put("mail.smtp.host", SMTPSERVER);
			// The Session class represents a mail session, It collects together
			// properties and defaults used by the mail API's
			Session session = Session.getDefaultInstance(props, null);
			// -- Create a new message --
			Message msg = new MimeMessage(session);
			// -- Set the FROM and TO fields --
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to, false));
			// -- We could include CC recipients too --
			// if (cc != null)
			// msg.setRecipients(Message.RecipientType.CC
			// ,InternetAddress.parse(cc, false));
			// -- Set the subject and body text --
			msg.setSubject(subject);
			msg.setText(body);
			// -- Set some other header information --
			msg.setHeader("X-Mailer", "LOTONtechEmail");
			msg.setSentDate(new Date());
			// -- Send the message --
			Transport.send(msg);
			System.out.println("Message sent OK.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}