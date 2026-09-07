package ex_17_OOPs;

public class LoginPage {
    public static void main(String[] args) {

        LoginPage l1 = new LoginPage("pramod@gmail.com", "123");       // Calls 2-parameter constructor

        System.out.println(l1.email);                                 // Prints l1 email
        System.out.println(l1.password);                              // Prints l1 password


        LoginPage l2 = new LoginPage("abc@gmail.com", "143", "Yes", 10); // Calls 4-parameter constructor

        System.out.println(l2.email);                                 // Prints l2 email
        System.out.println(l2.password);                              // Prints l2 password
        System.out.println(l2.submitButton);                          // Prints l2 submit button
        System.out.println(l2.a);                                     // Prints l2 a

    }

    String email;                                                   // Instance variable
    int a;                                                         // Instance variable

    LoginPage() {                                                 // No-Argument Constructor
        System.out.println("DC");                                // Prints DC
    }

    public LoginPage(String email, String password) {                     // 2-parameter constructor

        this.email = email;                                             // Assigning email
        this.password = password;                                      // Assigning password
    }

    String password;                                                 // Instance variable

    public LoginPage(String email, String password, String submitButton, int a) {

        //this.email = email;              // We can write these two lines
        //this.password = password;       // Separately or we can use  this(email, password);

        this(email, password);          // this.email = email; this.password = password;  or this(email, password);  both are same
                                       // and this(email, password); This is Constructor chaining → calls 2-parameter constructor

        this.submitButton = submitButton;                             // Assigning submitButton
        this.a = a;                                                  // Assigning a
    }


    String submitButton;                                           // Instance variable
}
