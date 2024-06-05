package Election;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Droptable {

    void droptable() {
        String url = "jdbc:mysql://localhost:3306/election";
        String user = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String dropparty = "DROP TABLE IF EXISTS partyDetails";
            String dropvote = "DROP TABLE IF EXISTS voteDetails";

            try (Statement statement = connection.createStatement()) {
                // Execute the drop table statements
                statement.executeUpdate(dropparty);
                statement.executeUpdate(dropvote);
                System.out.println("voteDetails & partyDetails dropped successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
