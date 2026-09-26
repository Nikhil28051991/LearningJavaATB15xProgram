package ex_24_Static;                                      // Package declaration

public class Lab196_Static {                              // Main class

    public static void main(String[] args) {              // Main method - program execution starts here

        Automation t1 = new Automation();                 // Creating an object of Automation class

        System.out.println(t1.driver);                    // Accessing static variable using object reference


        System.out.println(Automation.driver);            // Accessing static variable using class name

        Automation.driver = "Firefox";                    // Changing the value of static variable using class name

        System.out.println(Automation.driver);            // Printing the updated value of static variable

        System.out.println(Automation.driver2);           // Printing driver2 - default value of String is null

    }                                                     // End of main method

}                                                         // End of Lab196_Static class

class Automation {                                        // Automation class

    static String driver = "Chrome";                      // Static variable with initial value "Chrome"

    static String driver2;                                // Static variable with default value null

}                                                         // End of Automation class