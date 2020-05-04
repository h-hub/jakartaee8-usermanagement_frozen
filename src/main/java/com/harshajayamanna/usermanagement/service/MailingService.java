package com.harshajayamanna.usermanagement.service;

import java.util.Properties;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class MailingService {
	
    private static final String senderEmail = "";//change with your sender email
    private static final String senderPassword = "";//change with your sender password

	public void sendSimpleMail(String to, String title, String html) throws MessagingException {


		Session session = createSession();

		// create message using session
		MimeMessage message = new MimeMessage(session);
		prepareEmailMessage(message, to, title, html);

		// sending message
		Transport.send(message);

	}

	private static void prepareEmailMessage(MimeMessage message, String to, String title, String html)
			throws MessagingException {
		message.setContent(html, "text/html; charset=utf-8");
		message.setFrom(new InternetAddress(senderEmail));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(title);
	}

	private static Session createSession() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");// Outgoing server requires authentication
		props.put("mail.smtp.starttls.enable", "false");// TLS must be activated
		props.put("mail.smtp.host", "smtp.mailgun.org"); // Outgoing server (SMTP) - change it to your SMTP server
		props.put("mail.smtp.port", "587");// Outgoing port

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, senderPassword);
			}
		});
		return session;
	}
}
