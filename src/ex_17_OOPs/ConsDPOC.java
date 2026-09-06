package ex_17_OOPs;

public class ConsDPOC {

    public static void main(String[] args) {


        // ============================================================
        // 1. NO-ARGUMENT / DEFAULT-STYLE CONSTRUCTOR - TIGER
        // ============================================================

        Tiger t1 = new Tiger();                              // 0 arguments → calls Tiger()

        System.out.println(t1.name);                         // Printing tiger name → Royal Bengal Tiger



        // ============================================================
        // 2. PARAMETERIZED CONSTRUCTOR - ELEPHANT
        // ============================================================

        Elephant e1 = new Elephant("Raju", 25);              // 2 arguments → calls Elephant(String, int)

        System.out.println(e1.name);                         // Printing elephant name → Raju

        System.out.println(e1.age);                          // Printing elephant age → 25



        // ============================================================
        // 3. CONSTRUCTOR OVERLOADING - BIKE
        // ============================================================

        Bike b1 = new Bike();                                // 0 arguments → calls Bike()

        Bike b2 = new Bike("Honda Shine");                   // 1 argument → calls Bike(String)

        Bike b3 = new Bike("Honda Unicorn", 160);            // 2 arguments → calls Bike(String, int)

        Bike b4 = new Bike("Hero Splendor", 125);            // 2 arguments → calls Bike(String, int)

        System.out.println(b1.brand);                        // Printing bike brand → Pulsar

        System.out.println(b2.brand);                        // Printing bike brand → Honda Shine

        System.out.println(b3.brand);                        // Printing bike brand → Honda Unicorn

        System.out.println(b4.brand);                        // Printing bike brand → Hero Splendor



        // ============================================================
        // 4. CONSTRUCTOR CHAINING - LOGIN PAGE
        // ============================================================

        LoginPage1 l1 = new LoginPage1();                    // Calling No-Argument Constructor

        System.out.println(l1.email);                        // Printing email

        System.out.println(l1.password);                     // Printing password

        System.out.println(l1.submitButton);                 // Printing submit button
    }
}


// ================================================================
// TIGER - NO-ARGUMENT / DEFAULT-STYLE CONSTRUCTOR
// ================================================================

class Tiger1 {

    String name;                                              // Instance variable

    Tiger1() {                                                  // No-Argument Constructor
        name = "Royal Bengal Tiger";                           // Assigning default value
    }
}


// ================================================================
// ELEPHANT - PARAMETERIZED CONSTRUCTOR
// ================================================================

class Elephant1 {

    String name;                                              // Instance variable for name
    int age;                                                   // Instance variable for age

    Elephant1(String nameGiven, int ageGiven) {                // Parameterized Constructor with 2 parameters
        this.name = nameGiven;                                // Assigning given name to instance variable
        this.age = ageGiven;                                  // Assigning given age to instance variable
    }
}


// ================================================================
// BIKE - CONSTRUCTOR OVERLOADING
// ================================================================

class Bike1 {

    String brand;                                             // Instance variable for bike brand


    Bike1() {                                                   // Constructor 1 → 0 parameters
        brand = "Pulsar";                                     // Assigning Pulsar
    }


    Bike1(String brandGiven) {                                 // Constructor 2 → 1 parameter
        this.brand = brandGiven;                              // Assigning given bike brand
    }


    Bike1(String brandGiven, int cc) {                         // Constructor 3 → 2 parameters
        this.brand = brandGiven;                              // Assigning given bike brand
        System.out.println(brandGiven + " -> " + cc + " CC"); // Printing bike and engine capacity
    }
}


// ================================================================
// LOGINPAGE1 - CONSTRUCTOR CHAINING
// ================================================================

class LoginPage2 {

    String email;                                             // Instance variable for email
    String password;                                          // Instance variable for password
    String submitButton;                                      // Instance variable for submit button


    LoginPage2() {                                             // Constructor 1 → 0 parameters
        this("abc@gmail.com");                                 // Calling Constructor 2
        System.out.println("No-Argument Constructor");        // This executes after Constructor 2 finishes
    }


    LoginPage2(String emailGiven) {                            // Constructor 2 → 1 parameter
        this(emailGiven, "12345");                             // Calling Constructor 3
        System.out.println("Email Constructor");              // This executes after Constructor 3 finishes
    }


    LoginPage2(String emailGiven, String passwordGiven) {     // Constructor 3 → 2 parameters
        this(emailGiven, passwordGiven, "Login");             // Calling Constructor 4
        System.out.println("Email + Password Constructor");    // This executes after Constructor 4 finishes
    }


    LoginPage2(String emailGiven, String passwordGiven, String submitButtonGiven) { // Constructor 4 → 3 parameters

        this.email = emailGiven;                              // Assigning email

        this.password = passwordGiven;                        // Assigning password

        this.submitButton = submitButtonGiven;                // Assigning submit button

        System.out.println("Main Constructor");               // Printing constructor message
    }

}
