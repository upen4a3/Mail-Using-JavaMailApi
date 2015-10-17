package edu.ucm.cs.lab2;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailAPI {	
	
	
	public static void send(Envelope envlope){
		try	{
			Properties props = System.getProperties();
			// -- Attaching to default Session, or we could start a new one --
			props.put("mail.smtp.host", envlope.destHost);
			props.put("mail.smtp.port", "25");
			Session session = Session.getDefaultInstance(props, null);
			// -- Create a new message --
			Message msg = new MimeMessage(session);
			// -- Set the FROM and TO fields --
			msg.setFrom(new InternetAddress(envlope.sender));
			msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(envlope.recipient, false));
			// -- Set the subject and body text --
			msg.setSubject(envlope.message.Headers);
			msg.setText(envlope.message.Body);
			// -- Set some other header information --
			msg.setSentDate(new Date());
			// -- Send the message --
			Transport.send(msg);
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
}
