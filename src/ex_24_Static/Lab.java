package ex_24_Static;                                      // Package declaration

public class Lab {                                        // Class declaration

    int nonstatic;                                        // Non-static/instance variable

    static String statica;                                // Static/class variable


    public static void main(String[] args) {              // Main method - program execution starts here

        int a = 10;                                       // Local variable with value 10

        // System.out.println(nonstatic);                // ❌ Cannot directly access non-static variable from static method

        System.out.println(statica);                     // ✅ Can directly access static variable from static method

    }                                                     // End of main method

}                                                         // End of Lab class