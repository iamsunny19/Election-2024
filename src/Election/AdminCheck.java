package Election;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class AdminCheck {

    public void adminCheck() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter password: ");
        String password = scanner.nextLine();

        // Confirmation message
        System.out.println("We are verifying your credentials, please wait...");

        String url = "jdbc:mysql://localhost:3306/election";
        String user = "root";
        String password1 = "root";

        try (Connection connection = DriverManager.getConnection(url, user, password1)) {
            String findSQL = "SELECT password FROM admin";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(findSQL)) {

                boolean isAdmin = false;
                while (resultSet.next()) {
                    String dbPassword = resultSet.getString("password");
                    if (password.equals(dbPassword)) {
                        isAdmin = true;
                        break;
                    }
                }

                if (isAdmin) {
                    System.out.println("______________________________________________");
                    System.out.println("Authentication successful!");
                    Thread.sleep(2000);
                } else {
                    System.out.println("______________________________________________");
                    System.out.println("Authentication failed!");
                    System.err.println("Thanks for Using Sunny's Software \n@All rights reserved");

                    System.exit(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("Error: Could not authenticate. Please try again later.");
            e.printStackTrace();
        }

        scanner.close();

        try {
            Thread.sleep(2000); // Pause for 2 seconds (if needed)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
