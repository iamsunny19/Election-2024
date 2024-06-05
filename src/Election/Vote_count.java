package Election;

import java.util.Scanner;

public class Vote_count {

    String vote_count(String uName, String uEmail,long uVoter) {
        System.out.println("*******************WELCOME TO THE Election*************************");
        Scanner scanner = new Scanner(System.in);
        String selectedParty = null;
        boolean isValidInput = false;

        // Create an instance of Addvote outside the loop
        Addvote av = new Addvote();

        while (!isValidInput) {
            System.out.println("Please select your party for VOTE--------");
            System.out.println(" *********************************** ");
            System.out.println("Please choose \n 1. BJP \n 2. INC \n 3. JDU \n 4. SP \n 5. RJD \n 6. NOTA");

            int no = scanner.nextInt();
            System.out.println("You selected: " + no);

            switch (no) {
                case 1:
                    selectedParty = "BJP";
                    break;
                case 2:
                    selectedParty = "INC";
                    break;
                case 3:
                    selectedParty = "JDU";
                    break;
                case 4:
                    selectedParty = "SP";
                    break;
                case 5:
                    selectedParty = "RJD";
                    break;
                case 6:
                    selectedParty = "NOTA";
                    break;
                default:
                    System.err.println("Wrong input. Please enter correctly.");
                    // Continue to prompt the user again
                    continue;
            }
            
            // Set flag to true since input is valid
            isValidInput = true;
        }
        
        // Close the scanner
        scanner.close();
        
        // Call addvote method with the selected party
        av.addvote(selectedParty,uName, uEmail,uVoter);
        
        return selectedParty;
    }
}
