package Election;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Runner extends Totalvoter {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ar = "\u001B[0m";
    public static final String ao = "\u001B[38;2;255;165;0m";

    void runner(int vote) {
        String url = "jdbc:mysql://localhost:3306/election";
        String user = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String findSQL = "SELECT BJP, INC, JDU, SP, RJD, NOTA FROM partyDetails";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(findSQL);

                // Initialize variables to keep track of winner and runner-up
                String winnerParty = "";
                int winnerVotes = Integer.MIN_VALUE;
                String runnerUpParty = "";
                int runnerUpVotes = Integer.MIN_VALUE;
                int totalVotes = 0;

                // Iterate through the ResultSet to access the data
                if (resultSet.next()) {
                    int BJP = resultSet.getInt("BJP");
                    int INC = resultSet.getInt("INC");
                    int JDU = resultSet.getInt("JDU");
                    int SP = resultSet.getInt("SP");
                    int RJD = resultSet.getInt("RJD");
                    int NOTA = resultSet.getInt("NOTA");

                    // Calculate total votes for each party
                    totalVotes = BJP + INC + JDU + SP + RJD + NOTA;
                    
                    // Find the winner
                    if (BJP > winnerVotes) {
                        winnerParty = "BJP";
                        winnerVotes = BJP;
                    }
                    if (INC > winnerVotes) {
                        winnerParty = "INC";
                        winnerVotes = INC;
                    }
                    if (JDU > winnerVotes) {
                        winnerParty = "JDU";
                        winnerVotes = JDU;
                    }
                    if (SP > winnerVotes) {
                        winnerParty = "SP";
                        winnerVotes = SP;
                    }
                    if (RJD > winnerVotes) {
                        winnerParty = "RJD";
                        winnerVotes = RJD;
                    }
                    if (NOTA > winnerVotes) {
                        winnerParty = "NOTA";
                        winnerVotes = NOTA;
                    }

                    // Find the runner-up
                    for (String party : new String[]{"BJP", "INC", "JDU", "SP", "RJD", "NOTA"}) {
                        int votes = resultSet.getInt(party);
                        if (!party.equals(winnerParty) && votes > runnerUpVotes) {
                            runnerUpParty = party;
                            runnerUpVotes = votes;
                        }
                    }

                    // Calculate margin and margin percentage
                    int margin = winnerVotes - runnerUpVotes;
                    double marginPercentage = (double) margin / totalVotes * 100;

                    // Print results if winner and runner-up are different
                    
                    
                    if (margin==0)
                     {
                        // If winner and runner-up have the same votes, it's a draw
                        System.out.println("It's a draw! Re-election needed.");
                        System.out.println(ao+"Thanks for Using Sunny's Software \n@All rights reserved"+ar);
                    Draw draw =new Draw();
                    draw.draw(winnerParty,totalVotes,winnerVotes,runnerUpParty,runnerUpVotes,marginPercentage,Totalvoter.per);
                     
                     }
                    else
                    {
                        System.out.println(ANSI_GREEN + "******* WINNER ******* : \n" + winnerParty + " (" + winnerVotes + " votes)" + ar);
                        System.out.println("_________________________________________________________");
                        System.out.println(ao + "Runner-up: " + runnerUpParty + " (" + runnerUpVotes + " votes)" + ar);
                        System.err.println("Total Margin: " + margin);
                        System.out.println(ANSI_GREEN + "Margin Percentage: " + ar + marginPercentage);
                        
                        SendResult sr = new SendResult();
                        sr.sendResult(winnerParty,totalVotes,winnerVotes,runnerUpParty,runnerUpVotes,marginPercentage,Totalvoter.per);
                        
                        System.out.println("ELECTION COMMISION IS SENDING THE COPY OF RESULT TO THE ALL VOTERS ");
                        
                        System.out.println("_________________________________________________________");
                        System.out.println("Thanks for Using Sunny's Software \n@All rights reserved");
                   
                    
                    
                    
                    
                    } 
                
                
                } else {
                    System.out.println("No data found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
