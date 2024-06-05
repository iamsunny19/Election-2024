package Election;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class No 
{

    private Scanner sc; // Declare a scanner

    public No(Scanner sc) {
        this.sc = sc; // Initialize the scanner in the constructor
    }

	
	void run()  {
		//System.out.println("You have chosen 'No'. This functionality is yet to be implemented.");
        System.out.println("Hey! please enter all the needed Details for Verifications:- ");
        System.out.println("__________________________________________________");

        Scanner sc = new Scanner(System.in);
       
        String uName;
        do {
            System.out.println("Enter your FULL name: ");
           uName = sc.nextLine();
            if (uName.isEmpty()) {
                System.err.println("Name cannot be empty. Please enter your FULL name.");
            }
        } while (uName.isEmpty());

        
        System.out.println("Enter your Voter_ID: ");
        long uVoter = sc.nextLong();
        sc.nextLine(); // Consume newline character

        
        
        String uEmail;
        do {
            System.out.println("Enter your Email: ");
            uEmail = sc.nextLine();
            if (uEmail.isEmpty()) {
                System.err.println("Email cannot be empty. Please enter your Email.");
            }
        } while (uEmail.isEmpty());
        
	
        System.out.println(" Your details are :- ");
        System.err.println("----------------------------------------------");
        
        System.out.println(" Your name is : " +uName);
 
        System.out.println(" Your Voter_ID is : " +uVoter);
        System.out.println(" Your Email is : " +uEmail);
        
        
        
        System.err.println(" Thanks for Sharing the Details");
        System.err.println("----------------------------------------------");
        System.out.println(" please wait , while verifiing your details: ");
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        Verify ver = new Verify();
        boolean isVerified = ver.verifyIgnoreCase(uName,uVoter,uEmail);

        if (!isVerified) {
            System.err.println("Your details are not correct. The program will exit.");
            System.exit(0); // Terminate the program
        }
        
        else {
        	
        	
        	//let check is this VOTER doing fraud
        	
        	  String url = "jdbc:mysql://localhost:3306/election";
              String user = "root";
              String password = "root";

              // Load the JDBC driver
              try {
                  Class.forName("com.mysql.cj.jdbc.Driver");
              } catch (ClassNotFoundException e) {
                  e.printStackTrace();
                 
              }

              // Establish the connection
              try (Connection connection = DriverManager.getConnection(url, user, password)) {
                  System.out.println("Connected to the database!");
                  
                  
               // SQL to select data from the table where Voter_id = 1234
                  String selectSQL = "SELECT Voter_id FROM voteDetails WHERE Voter_id = ?";

               
               // Prepare the statement for selection
                  try (PreparedStatement selectStatement = connection.prepareStatement(selectSQL)) {
                      // Set parameter for the prepared statement
                      selectStatement.setLong(1, uVoter); // Assuming uVoter is the desired Voter_id

                      // Execute the selection
                      ResultSet resultSet = selectStatement.executeQuery();

                      // Process the result
                      if (resultSet.next())
                      {
                          long voterId = resultSet.getLong("Voter_id");
                         // System.out.println("Voter_id found: " + voterId);
                         
                          System.err.println("You are doing Fraud!! , We are Callig Police ");
                          System.err.println(" We are stoping voting for you ");
                          System.err.println(" Dialing 📞(100)...... ");
                          System.exit(0);
                          
                          
                      } else
                       {
                         
                    	  System.err.println(" You can CAST your vote now");
                      }
                  }
        	
        	
        
			
		}
              
              catch (SQLException e) {
            	    // Handle exceptions if any
            	    e.printStackTrace();
            	}
        
       
       //here we'll cast the vote 
        Vote_count vCount = new Vote_count();
        vCount.vote_count(uName,uEmail,uVoter);
        
       
        		
	}
 }
}
