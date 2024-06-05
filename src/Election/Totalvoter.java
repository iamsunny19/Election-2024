package Election;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Totalvoter {
	
	 public static double per;
    void Totalvoter(int vote) { // Constructor accepting the int parameter vote
        String url = "jdbc:mysql://localhost:3306/election";
        String user = "root";
        String password = "root";

        // Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return; // Exit the method if driver loading fails
        }

        // Establish the connection and execute the query
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String selectSQL = "SELECT COUNT(Voter_ID) AS TotalVoters FROM userDetails";
            try (PreparedStatement statement = connection.prepareStatement(selectSQL)) {
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int totalVoters = resultSet.getInt("TotalVoters");
                    System.out.println("Total Voters listed in Our Portal : " + totalVoters);

                    per = vote / (double) totalVoters * 100;
                    System.err.println("Voting Percentage :" + per);
                    
                   
                    
                    
                    
                } else {
                    System.out.println("No data found for total voters.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
