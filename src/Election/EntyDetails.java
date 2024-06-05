package Election;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class EntyDetails {

    private Scanner sc;

    public EntyDetails(Scanner sc) {
        this.sc = sc;
    }

    void run() {
        System.out.println("Hey! please enter all the needed Details for Registration:- ");
        System.out.println("__________________________________________________");

        String uName;
        do {
            System.out.println("Enter your FULL name: ");
            uName = sc.nextLine();
            if (uName.isEmpty()) {
                System.err.println("Name cannot be empty. Please enter your FULL name.");
            }
        } while (uName.isEmpty());

        
        
        
        int uAge;
        while (true) {
            System.out.println("Enter your Age: ");
            if (sc.hasNextInt()) {
                uAge = sc.nextInt();
                if (uAge >= 18 && uAge <= 123) {
                    break; // Valid age, exit loop
                } else if (uAge > 123) {
                    System.err.println("Bravo! You are the oldest person alive in this World,\n please contact Guinness World Records 🥇, and then apply for registration.");
                    return;
                } else {
                    System.err.println("Age must be 18 or older. Please enter a valid age!");
                }
            } else {
                System.err.println("Invalid input. Please enter a valid integer.");
                sc.next(); // Consume invalid input
            }
        }
        sc.nextLine(); // Consume newline character
        
        
        
        long uID_NO;
        String len;
        do {
            System.out.println("Enter your AADHAR ID_NO: ");
            uID_NO = sc.nextLong();
            sc.nextLine(); // Consume newline character
            len = Long.toString(uID_NO);

            if (len.length() != 12) {
                System.err.println("Please enter a correct AADHAR ID, it should be 12 digits.");
            }
        } while (len.length() != 12);

      

        
        
        String uEmail;
        do {
            System.out.println("Enter your Personal Email id: ");
            uEmail = sc.nextLine();
            if (uEmail.isEmpty()) {
                System.err.println("Email cannot be empty. Please enter your Email.");
            
                
            }
            
         
            //here we'll validate Email id
            
            Mailverify mv =new Mailverify();
            
            
            System.err.println(" You have entered Valid Mail! , Thank you ");
            
        } while (uEmail.isEmpty());
        
        

         // Assuming uEmail is the user's email address
        
        // Database URL, username, and password
        String url = "jdbc:mysql://localhost:3306/election";
        String user = "root";
        String password = "root";

        // Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Establish the connection
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database!");

            // Create a statement
            Statement statement = connection.createStatement();

            // Create a table
            String createTableSQL = "CREATE TABLE IF NOT EXISTS userDetails (\r\n"
                    + "    Name VARCHAR(50) NOT NULL,\r\n"
                    + "    Age INT NOT NULL,\r\n"
                    + "    ID_NO BIGINT,\r\n"
                    + "    Email VARCHAR(255) NOT NULL unique,\r\n"
                    + "    Voter_ID BIGINT NOT NULL unique,\r\n"
                    + "    PRIMARY KEY (ID_NO)\r\n"
                    + ")";
            statement.execute(createTableSQL);

           // System.out.println("Checked for table existence and created if not present.");
          
            
            
            
            
            
            
            
            
            // Insert data using PreparedStatement to avoid SQL injection
            String insertSQL = "INSERT INTO userDetails (Name, Age, ID_NO, Email ,Voter_ID) "
                    + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, uName);
            preparedStatement.setInt(2, uAge);
           // preparedStatement.setString(3, uID_TYPE);
            preparedStatement.setLong(3, uID_NO);
            preparedStatement.setString(4, uEmail);
            
            
            //here we will call to take id from voter id.
            
            Voterid vi = new Voterid();
            long uVoter= vi.id();
            preparedStatement.setLong(5, uVoter);
            
            
            preparedStatement.executeUpdate();
            System.out.println("Data inserted!");
            
          
            
            System.err.println("----------------------------------------------");

            // Query the data for the just inserted user
            String selectSQL = "SELECT * FROM userDetails WHERE ID_NO = ? AND Email = ?";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setLong(1, uID_NO);
            preparedStatement.setString(2, uEmail);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set
            if (resultSet.next()) {
                System.out.println("Name: " + resultSet.getString("Name"));
                System.out.println("Age: " + resultSet.getInt("Age"));
               
                System.out.println("AdharID_NO: " + resultSet.getLong("ID_NO"));
                System.out.println("Email: " + resultSet.getString("Email"));
                System.out.println("ID_NO: " + resultSet.getLong("ID_NO"));
                System.out.println("Voter_ID: " + resultSet.getLong("Voter_ID"));
                System.err.println("__________________________________________");
                
                
             // After successfully inserting into the database, send registration confirmation email
                Regmail rm = new Regmail();
                rm.display(uName, uEmail,uVoter);
                
                Thread.sleep(2000);
                
                System.out.println("Now you Can Vote!!");
                System.err.println("*******************************************************");
                //let user gave the vote
                No vote = new No(sc);
                vote.run();
                
                
                
            } else {
                System.err.println("Data insertion failed. Please try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	
}
