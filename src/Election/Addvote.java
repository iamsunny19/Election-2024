package Election;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Addvote {

    public String addvote(String vote,String name, String email,long uVoter) {
        // Database URL, username, and password
        String url = "jdbc:mysql://localhost:3306/election";
        String user = "root";
        String password = "root";

        // Ensure vote is a valid column name to prevent SQL injection
        if (!isValidVoteColumn(vote)) {
            System.out.println("Invalid vote option: " + vote);
            return null;
        }

        // Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        // Establish the connection
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database!");
            
            
            Statement Statement = connection.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS partyDetails (" +
                    "BJP INT DEFAULT 0, " +
                    "INC INT DEFAULT 0, " +
                    "JDU INT DEFAULT 0, " +
                    "SP INT DEFAULT 0, " +
                    "RJD INT DEFAULT 0, " +
                    "NOTA INT DEFAULT 0)";
            Statement.execute(createTableSQL);

               String insertInitialRowSQL = "INSERT INTO partyDetails (BJP, INC, JDU, SP, RJD, NOTA) VALUES (0, 0, 0, 0, 0, 0)";
               Statement.execute(insertInitialRowSQL);
           // System.out.println("Checked for table existence and created if not present.");
                
            // Update the vote count in the table
            String updateSQL = "UPDATE partyDetails SET " + vote + " = " + vote + " + 1";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Hi "+ name+"/"+email+",\nYour Vote recorded successfully for " + vote + "!");
                 //return vote; // Return the party name
                
                //if vote has been cast add same details in votedetails table

             // Establish the connection
                try (Connection connection1 = DriverManager.getConnection(url, user, password)) {
                    // Create a statement object
                    try (Statement statement1 = connection1.createStatement()) {
                        // SQL to create the table if not exists
                        String createTableSQL1 = "CREATE TABLE IF NOT EXISTS voteDetails (" +
                                "Name varchar(50) not null, " +
                                "Voter_id BIGINT primary key, " +
                                "Email varchar(50) unique)";
                        // Execute the SQL for creating the table
                        try {
                            statement1.execute(createTableSQL1);
                        } catch (SQLException e) {
                            // Check if the exception is specifically about the absence of the votedetails table
                            if (e.getMessage().contains("Table 'election.votedetails' doesn't exist")) {
                                System.err.println("Table 'votedetails' was not there, So We are Creating again!!");
                                // Create the voteDetails table
                                statement1.execute(createTableSQL1);
                            } else {
                                // Handle the exception gracefully without printing the stack trace
                                System.err.println("Error occurred while creating the voteDetails table: " + e.getMessage());
                            }
                        }
                                        
                        // SQL to insert data into the table
                        String insertSQL = "INSERT INTO voteDetails (Name, Voter_id, Email) VALUES (?, ?, ?)";

                        // Prepare the statement for insertion
                        try (PreparedStatement preparedStatement1 = connection1.prepareStatement(insertSQL)) {
                            // Set parameters for the prepared statement
                            preparedStatement1.setString(1, name);
                            preparedStatement1.setLong(2, uVoter);
                            preparedStatement1.setString(3, email);

                            // Execute the insertion
                            preparedStatement1.executeUpdate();

                            // Inform user about successful insertion
                            System.out.println("Data inserted successfully.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                
                
                
                Voted v = new Voted();
                v.voted(vote,name,email);
                
                
            } else {
                System.out.println("Failed to record vote!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        	 
        }
        return null;
    }

    private boolean isValidVoteColumn(String vote) {
        // List of valid columns
        String[] validVotes = {"BJP", "INC", "JDU", "SP", "RJD", "NOTA"};
        for (String validVote : validVotes) {
            if (validVote.equals(vote)) {
                return true;
            }
        }
        return false;
       
        
        
        
    }
    
    
}

