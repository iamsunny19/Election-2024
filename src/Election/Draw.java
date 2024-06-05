package Election;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

public class Draw {
   public void draw(String winner , int totalVotes, int winnerVotes, String runner, int runnerUpVotes, double marginPercentage, double per) {
        //System.err.println("The Result has been Sent through Mail to all the VOTERS");

        String url = "jdbc:mysql://localhost:3306/election";
        String user = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String findSQL = "SELECT Name, Email FROM voteDetails";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(findSQL);

                while (resultSet.next()) {
                    String name = resultSet.getString("Name");
                    String email = resultSet.getString("Email");
                    

                    // Construct email message
                    String subject = "Election Result 2024";
                    String message = "Dear " + name.toUpperCase() + ",\n\n";
                    
                    message += "Election RESULT : DRAW between "+winner+" & "+runner+"\n\n";
                    message += "Summary of ELECTION 2024 :\n";
                    message += "_________________________________\n";
                    message += "Total Votes : "+totalVotes+"\n";
                    message += "Margin Percentage : "+marginPercentage+"\n";
                    message += "Voting Percentage : "+per+"\n\n";
                    message += "OVERALL_RESULT  :- Need to do re-Election\n\n";
                   
                    message += "Thanks & Regards,\nELECTION COMMISSION OF INDIA";

                  
                    
                    // Send email
                    sendEmail(email, subject, message);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to send email
 // Method to send email
    private void sendEmail(String email, String subject, String message) {
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
            mimeMessage.setFrom(new InternetAddress("electionresultgov@gmail.com"));

            // Adding recipient to message
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

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
            System.out.println("Sent message successfully to " + email);
            
            Droptable drop =new Droptable();
            drop.droptable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}

