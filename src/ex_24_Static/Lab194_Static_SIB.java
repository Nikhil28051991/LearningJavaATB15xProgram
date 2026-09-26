package ex_24_Static;                                      // Package declaration

public class Lab194_Static_SIB {                          // Main class

    public static void main(String[] args) {              // Main method - program execution starts here

        A a = new A();                                    // Creating an object of class A - IIB will execute

    }                                                     // End of main method

}                                                         // End of Lab194_Static_SIB class


class A {                                                 // Creating class A


    // ============================================================
    // SIB = Static Initialization Block
    // ============================================================

    static {                                              // SIB - Static Initialization Block

        System.out.println("Called only Once when Class is loaded"); // SIB executes only once when class A is loaded

        System.out.println("You can write a code reading a excel, file, , database file"); // Used for static initialization code

    }                                                     // End of SIB


    // ============================================================
    // IIB = Instance Initialization Block
    // ============================================================

    {                                                     // IIB - Instance Initialization Block

        System.out.println("IIB");                        // IIB executes when an object of class A is created

        // What is the purpose?                           // IIB can be used for initialization before constructor execution

        // Here you can write code related to              // You can write initialization-related code here

        // start a website or anything before starting the // Example: prepare something before automation starts

        // web automation or api automation               // Example: Web/API automation setup code

    }                                                     // End of IIB


    static int a = 10;                                    // Static variable - belongs to the class


    static void m1() {                                    // Static method - belongs to the class

        System.out.println("static functionc");           // Printing message from static method

    }                                                     // End of static method m1


}                                                         // End of class A