package ex_17_OOPs;

public class LoginPage1 {

    String email;                                    // Instance variable for email
    String password;                                // Instance variable for password
    String submitButton;                            // Instance variable for submit button


    LoginPage1() {                                   // Constructor 1: No arguments
        this("abc@gmail.com");                       // Calling Constructor 2 with email
        System.out.println("No-Argument Constructor");
    }


    LoginPage1(String emailGiven) {                  // Constructor 2: One argument
        this(emailGiven, "12345");                   // Calling Constructor 3 with email and password
        System.out.println("Email Constructor");
    }


    LoginPage1(String emailGiven, String passwordGiven) { // Constructor 3: Two arguments
        this(emailGiven, passwordGiven, "Login");    // Calling Constructor 4 with 3 arguments
        System.out.println("Email + Password Constructor");
    }


    LoginPage1(String emailGiven, String passwordGiven, String submitButtonGiven) { // Constructor 4
        this.email = emailGiven;                     // Assigning email
        this.password = passwordGiven;               // Assigning password
        this.submitButton = submitButtonGiven;       // Assigning submit button
        System.out.println("Main Constructor");
    }
}

class Main5 {

    public static void main(String[] args) {

        LoginPage1 l1 = new LoginPage1();             // Starts Constructor Chaining

        System.out.println(l1.email);                 // Printing email
        System.out.println(l1.password);              // Printing password
        System.out.println(l1.submitButton);          // Printing submit button
    }
}