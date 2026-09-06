package ex_17_OOPs;

public class Bike {

    String brand;                                    // Instance variable for bike brand


    Bike() {                                         // Constructor 1: No arguments
        brand = "Pulsar";                            // Default bike is Pulsar
    }


    Bike(String brandGiven) {                        // Constructor 2: One argument
        brand = brandGiven;                          // Assigning given brand
    }


    Bike(String brandGiven, int cc) {                // Constructor 3: Two arguments
        brand = brandGiven;                          // Assigning given brand
        System.out.println("Engine: " + cc + " CC"); // Printing engine capacity
    }
}

class Main3 {

    public static void main(String[] args) {

        Bike b1 = new Bike();                         // Calls Bike() → Pulsar

        Bike b2 = new Bike("Honda Shine");            // Calls Bike(String)

        Bike b3 = new Bike("Honda Unicorn", 160);     // Calls Bike(String, int)

        Bike b4 = new Bike("Hero Splendor", 125);     // Calls Bike(String, int)

        System.out.println(b1.brand);                 // Output: Pulsar

        System.out.println(b2.brand);                 // Output: Honda Shine

        System.out.println(b3.brand);                 // Output: Honda Unicorn

        System.out.println(b4.brand);                 // Output: Hero Splendor
    }
}