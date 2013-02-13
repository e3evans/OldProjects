package com.aurora.formemail.services;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;

@Path("/email")
@Produces("text/xml")
public class FormEmailService {

	private static final String DEFAULT_MAIL_SERVER = "xx";
	private static final String MAIL_SUBJECT = "xx";
	private static final String FIRST_NAME = "acgc_share_name";	
	private static final String LAST_NAME = "acgc_share_lastname";
	private static final String LOCATION = "acgc_share_location";
	private static final String PHONE="acgc_share_phone";
	private static final String TITLE="acgc_share_title";
	private static final String COMMENT = "acgc_share_comment";
	private static final String SENDTO = "acgc_send_to";
	private static final String SENDFROM = "acgc_send_from";
	
	@POST
	@Path("/connectwithus")
	public void sendEmail(MultivaluedMap<String, String> form){

		try {
			// Get system properties
			StringBuffer textmsg = new StringBuffer();
			Properties properties = System.getProperties();

			properties.setProperty("mail.aurora.org", DEFAULT_MAIL_SERVER);

			Session session = Session.getDefaultInstance(properties);

			MimeMessage message = new MimeMessage(session);			
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(decodeUTF8(form.getFirst(SENDTO))));
			String fromAddr=decodeUTF8(form.getFirst(SENDFROM));
			String senderName="caregiverPortal@aurora.org";
			
			String userID= form.getFirst(LAST_NAME)+", "+form.getFirst(FIRST_NAME);
			
				
		
			message.setFrom((new InternetAddress(fromAddr)));
			
            
            if ((null == senderName) || (" " == senderName)||("Name".equalsIgnoreCase(senderName))) {
            	message.setSubject(MAIL_SUBJECT + " from - "+userID);
			} else {
				message.setSubject(MAIL_SUBJECT + " "+form.getFirst(TITLE)+"from - "+userID+" by -"+senderName);
			}

			if ((null == form.getFirst(COMMENT)) || (" " == form.getFirst(COMMENT))||("Enter message here".equalsIgnoreCase(form.getFirst(COMMENT)))) {
				message.setText("No message Entered");
			} else{
				textmsg.append(form.getFirst(LOCATION));
				textmsg.append(form.getFirst(PHONE));
				textmsg.append(form.getFirst(COMMENT));
				message.setText(textmsg.toString());
			}
			// Send message
			Transport.send(message);


		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private String decodeUTF8(String input){	
		try {
			input =URLDecoder.decode(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return input; 
		
	}
}
