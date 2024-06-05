package Election;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Totalvotepartywise {
    public static final String ao = "\u001B[38;2;255;165;0m";
    public static final String ar = "\u001B[0m";
    public static final String ag = "\u001B[32m";
    

    void totalvotepartywise() {
        // Database URL, username, and password
        String url = "jdbc:mysql://localhost:3306/election";
        String user = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String findSQL = "SELECT * FROM partyDetails LIMIT 1";

            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(findSQL);

                // Iterate through the ResultSet to access the data
                while (resultSet.next()) {
                    // Assuming there are six columns: BJP, INC, JDU, SP, RJD, NOTA
                    int BJP = resultSet.getInt("BJP");
                    int INC = resultSet.getInt("INC");
                    int JDU = resultSet.getInt("JDU");
                    int SP = resultSet.getInt("SP");
                    int RJD = resultSet.getInt("RJD");
                    int NOTA = resultSet.getInt("NOTA");

                    // Print the data from the first row
                    int vote = BJP + INC + JDU + SP + RJD + NOTA;
                    System.out.println(ag + "Total numbers of voting have been done: " + ar + vote);

                    System.out.println(ao + "BJP Votes: " + ar + BJP + ", \n" +
                            ao + "INC Votes: " + ar + INC + ", \n" +
                            ao + "JDU Votes: " + ar + JDU + ", \n" +
                            ao + "SP Votes: " + ar + SP + ", \n" +
                            ao + "RJD Votes: " + ar + RJD + ", \n" +
                            ao + "NOTA Votes: " + ar + NOTA);

                    Totalvoter tvPer = new Totalvoter();
                    tvPer.Totalvoter(vote);

                    System.out.println("_________________________________________________________");

                    Runner runner = new Runner();
                    runner.runner(vote);
                }
            }
        } catch (SQLException e) {
           
           //e.printStackTrace();

                System.err.println("The Voting has not been Completed YET 😀");
                System.err.println("Thanks for Using Sunny's Software \n@All rights reserved");
                System.exit(0);
            }
        }
    }

