package Election;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Verify {

    public boolean verifyIgnoreCase(String uName,long uVoter,String uEmail) {
        // Database URL, username, and password
        String url = "jdbc:mysql://localhost:3306/election";
        String user = "root";
        String password = "root";

        // Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        // Establish the connection
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database!");

            // Create a prepared statement with case-insensitive query
            String selectSQL = "SELECT * FROM userDetails WHERE LOWER(Name) = LOWER(?) AND Voter_ID = ? AND LOWER(Email) = LOWER(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, uName);

            preparedStatement.setLong(2, uVoter);
            preparedStatement.setString(3, uEmail);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set
            if (resultSet.next()) {
                System.out.println("Name: " + resultSet.getString("Name"));

                System.out.println("Voter_ID: " + resultSet.getLong("Voter_ID"));
                Thread.sleep(2000);
                System.out.println("Email: " + resultSet.getString("Email"));
                return true;
            } else {
                System.err.println("Details haven't matched.");
                System.err.println("You can't vote right now.");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
