package Election;

import java.util.Scanner;

public class Admin {
    public static final String AR = "\u001B[0m";
    public static final String AO = "\u001B[38;2;255;165;0m";

    void admin() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Hi, ADMIN!!");
            System.err.println(" If you wanna to Declare the Result ");
            AdminCheck aCheck = new AdminCheck();
            aCheck.adminCheck();

            System.out.println(AO + "Let's check the RESULT of Election 2024" + AR);
            
            System.err.println("____________________________________________________");
         
                    System.out.println("**********************************************************");
                    System.out.println(" We are Going to DECLARE the RESULT of ELECTION ");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("**********************************************************");
                    Totalvotepartywise tv = new Totalvotepartywise();
                    tv.totalvotepartywise();
                    
               
            }
        } 
    }

