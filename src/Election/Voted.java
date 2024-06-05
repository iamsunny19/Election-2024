package Election;

public class Voted {

    void voted(String vote, String name, String email) {
        Mail mail = new Mail();
        mail.sendConfirmationEmail(vote, name, email);
        System.err.println("@All rights reserved");
        System.out.println("Hi " + name + "/" + email + ",\nYour vote recorded successfully for " + vote + "!");
    }
}
