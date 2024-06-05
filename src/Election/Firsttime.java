package Election;

import java.util.Scanner;

public class Firsttime {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // Create a single scanner instance
        EntyDetails ed = new EntyDetails(sc); // Pass the scanner to EntyDetails
        No n = new No(sc); // Pass the scanner to No

        System.out.println("Hi, Welcome!! ");
        System.out.println(" Please verify yourself, Who you are ? \n1. Admin \n2. VOTER");
        int r = sc.nextInt();
        sc.nextLine();
        if (r == 1) {
            Admin ad = new Admin();
            ad.admin();
        } else {
            System.out.println("Are you going to Vote for the first time (YES/NO)? ");
            String res1 = sc.nextLine().toLowerCase(); // Read input and convert to lowercase

            switch (res1) {
                case "yes":
                    System.out.println("Have you registered yourself for VOTE? (Y/N)");
                    char res = sc.next().charAt(0); // Read the next character
                    sc.nextLine(); // Consume newline left-over

                    try {
                        switch (res) {
                            case 'N':
                            case 'n':
                                ed.run(); // Run EntyDetails if not registered
                                break;
                            case 'Y':
                            case 'y':
                                n.run(); // Run No if already registered
                                break;
                            default:
                                throw new IllegalArgumentException("Unexpected value: " + res);
                        }
                    } catch (Exception e) {
                        System.err.println("An error occurred: " + e.getMessage());
                        System.err.println("We are expecting only Y and N. \n*******Try Again******");
                    }
                    break;

                case "no":
                    n.run(); // Run No if not voting for the first time
                    break;

                default:
                    System.err.println("Unexpected value: " + res1);
                    System.out.println("Please enter YES or NO.");
                    break;
            }
        }

        sc.close(); // Close the scanner after all operations are done
    }
}

