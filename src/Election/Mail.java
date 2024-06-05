package Election;

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

public class Mail {

    public void sendConfirmationEmail(String vote, String uName, String uEmail) {
        System.out.println("Confirmation email has been sent to: " + uEmail);
        System.out.println("Preparing to send message...");
        String message = "Hello " + uName.toUpperCase() + ", \n" +
                "\n" +
                "You have voted for:  " + vote + "\n" +
                "\n" +
                "\n" +
                "Thanks & Regards,\n" +
                "ELECTION COMMISSION OF INDIA";
        String subject = "Election: Vote Confirmation";
        String from = "electionresultgov@gmail.com";
        String to = uEmail;

        sendEmail(message, subject, to, from);
    }

    private static void sendEmail(String message, String subject, String to, String from) {
        String host = "smtp.gmail.com";

        // Get the system properties
        Properties properties = System.getProperties();

        // Setting important information to properties object
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Step 1: To get the session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("electionresultgov@gmail.com", "chaz yfrr ilwd dsnb");
            }
        });

        session.setDebug(true);

        // Step 2: Compose the message (text, multi-media)
        MimeMessage mimeMessage = new MimeMessage(session);

        try {
            // From email
            mimeMessage.setFrom(new InternetAddress(from));

            // Adding recipient to message
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Adding subject to message
            mimeMessage.setSubject(subject);

            // Create the message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(message);

            // Create a multipart message
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            mimeMessage.setContent(multipart);

            // Send message
            Transport.send(mimeMessage);
            System.out.println("Sent message successfully...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
