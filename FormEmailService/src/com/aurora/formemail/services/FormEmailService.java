package com.aurora.formemail.services;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/email")
public class FormEmailService {

	private static final String DEFAULT_MAIL_SERVER = "xx";
	private static final String MAIL_FROM = "xx";
	private static final String MAIL_TO = "xx";
	private static final String MAIL_SUBJECT = "xx";
	
	@POST
	public void sendEmail(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		java.io.PrintWriter out = response.getWriter();
		try {

			// Get system properties
			String txtmsg="";
			Properties properties = System.getProperties();

			properties.setProperty("mail.smtp.host", DEFAULT_MAIL_SERVER);

			Session session = Session.getDefaultInstance(properties);

			MimeMessage message = new MimeMessage(session);

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(MAIL_TO));

			String fromAddr=request.getParameter("mail_from");
			String senderName=request.getParameter("sender_name");
			txtmsg = request.getParameter("mail_text");
			String userID= request.getParameter("userID");
			
				
			if (null != fromAddr) {
				message.setFrom((new InternetAddress(fromAddr)));
			} else {
				message.setFrom((new InternetAddress(MAIL_FROM)));
			}
            
            if ((null == senderName) || (" " == senderName)||("Name".equalsIgnoreCase(senderName))) {
            	message.setSubject(MAIL_SUBJECT + " from store#- "+userID);
			} else {
				message.setSubject(MAIL_SUBJECT + " from store#- "+userID+" by -"+senderName);
			}

			if ((null == txtmsg) || (" " == txtmsg)||("Enter message here".equalsIgnoreCase(txtmsg))) {
				message.setText(" ");
			} else{
				message.setText(txtmsg);
			}
			// Send message
			Transport.send(message);
			out.write("The message is sent successfully.");

		} catch (Exception ex) {
			//out.write("The message failed to send.");
			ex.printStackTrace();
		}
		
	}
}
