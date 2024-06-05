package Election;

import java.io.*;

public class Voterid {
    
    // ANSI escape code for green text
    public static final String ag = "\u001B[32m";
    // ANSI escape code to reset color
    public static final String ar = "\u001B[0m";
    
    private long currentId;
    private String filename = "voterid.txt"; // File to store the current ID
    
    public Voterid() {
        // Load the current ID from the file, or set it to a default value if the file doesn't exist
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            currentId = Long.parseLong(reader.readLine());
            reader.close();
        } catch (IOException | NumberFormatException e) {
            // If there's an error reading from the file, set the currentId to a default value
            currentId = 12345;
        }
    }
    
    public long id() {
        long id = currentId;
        currentId++;
       // System.out.println(ag + "Your Voter id is: " + ar + id);
        //System.out.println("Don't share with anyone");
        System.err.println("--------------------------------------------------");
        
        
        
        // Write the updated currentId back to the file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(Long.toString(currentId));
            writer.close();
        } catch (IOException e) {
            // Handle the error if unable to write to the file
            e.printStackTrace();
        }
        
        return id;
    }
}
