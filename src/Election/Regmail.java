package Election;

import java.io.File;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
public class Regmail {

	public void display(String uName,String uEmail,long uVoter) {
		    System.out.println("Confirmation email of Registration has been sent to: " + uEmail);		System.out.println("preparing to send message ...");
			String message = "Hello "+uName.toUpperCase()+", \n "+" \n "+" We have registered you as a voter.\n  Your VOTER_ID is :"+uVoter+" \n" +" \n "+ " \n" +"Thanks & Regards,\n" +"ELECTION COMMISION OF INDIA" ;
			String subject = "Election : Registration_Confirmation";
			String to = uEmail;
			String from = "electionresultgov@gmail.com";
			
//			sendEmail(message,subject,to,from);
			sendEmail(message,subject,to,from);
		}

	private static void sendEmail(String message, String subject, String to, String from)

	{
		String host="smtp.gmail.com";
		
		//get the system properties
		Properties properties = System.getProperties();
		System.out.println("PROPERTIES "+properties);
		
		//setting important information to properties object
		
		//host set
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port","465");
		properties.put("mail.smtp.ssl.enable","true");
		properties.put("mail.smtp.auth","true");
		
		
		//Step 1: to get the session object..
			
		
		Session session=Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {				
				return new PasswordAuthentication("electionresultgov@gmail.com","chaz yfrr ilwd dsnb");
			}
			
			
			
		});
				
		
		session.setDebug(true);
		
		//Step 2 : compose the message [text,multi media]
		MimeMessage m = new MimeMessage(session);
		
		try {
		
		//from email
		m.setFrom(from);
		
		//adding recipient to message
		m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		
		//adding subject to message
		m.setSubject(subject);

	    // Create the message part
	    MimeBodyPart messageBodyPart = new MimeBodyPart();
	    messageBodyPart.setText(message);

	    // Create a multipart message
	    MimeMultipart multipart = new MimeMultipart();
	    multipart.addBodyPart(messageBodyPart);

	    // Send the complete message parts
	    m.setContent(multipart);

	    // Send message
	    Transport.send(m);
	    System.out.println("Sent message successfully....");

		
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		

		
		
	}

		
	}
	
	

