package ex_24_Static;                                      // Package name

public class Lab193_Static_P2 {                            // Main class

    public static void main(String[] args) {               // Main method - program execution starts here

        ATB a1 = new ATB();                                // Creating ATB object

        a1.phone_np = 9876543210L;                        // Assigning phone number
        a1.name = "Nikhil";                                // Assigning name

        System.out.println(ATB.course_name);              // Accessing static variable using class name

        ATB.markAttendance();                             // Calling static method using class name

        a1.display();                                     // Calling non-static method using object

    }                                                     // End of main method
}                                                         // End of Lab193_Static_P2 class


class ATB {                                                // Creating ATB class

    long phone_np;                                         // Instance variable - stores phone number

    String name;                                           // Instance variable - stores student name

    static String course_name = "ATB";                    // Static variable - shared by all ATB objects


    static void markAttendance() {                        // Static method

        System.out.println("Mark Attendance");            // Printing attendance message

        // System.out.println(this.phone_np);             // ❌ Cannot use 'this' inside static method

    }                                                     // End of markAttendance method


    void display() {                                      // Non-static/instance method

        System.out.println(this.phone_np + " " + this.name + " " + course_name); // Accessing instance and static variables

    }                                                     // End of display method


    static class A {                                      // Static nested class

    }                                                     // End of static class A

}                                                         // End of ATB class