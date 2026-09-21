package ex_23_OOPs_Super_Abstraction.superKeyword;

public class Lab182_Abs {                          // Public class
}

class Vehicle {                                   // Parent class

    public int maxSpeed = 180;                   // Variable from Parent class

    Vehicle() {                                  // Default Constructor from Parent class
        System.out.println("Default Const");
    }

    Vehicle(int a) {                             // Parameterized Constructor from Parent class
        System.out.println("Param Con");
    }

    Vehicle(int a, int b) {                      // Parameterized Constructor from Parent class - 2 Arguments
        System.out.println("Param Con");
    }

    void message() {                             // Method from Parent class - No Argument
        System.out.println("No Return, No Argument");
    }

    void message(int a) {                        // Method from Parent class - 1 Argument / Method Overloading
        System.out.println("PC - argument");
    }

    void drive() {                               // Method from Parent class
        System.out.println("Vehicle Parent");
    }

    void noTest() {                              // Method from Parent class
        System.out.println("Empty!");
    }
}

class Car extends Vehicle {                     // Child class

    private int maxSpeed = 281;                 // Variable from Child class

    Car() {                                     // Default Constructor from Child class

        super(100);                          // Calling Parameterized Constructor from Parent class
    }

    Car(int a) {                                 // Parameterized Constructor from Child class
        System.out.println("PC Car");
    }

    void test() {                                // Method from Child class
    }

    public static void main(String[] args) {     // Main Method from Child class

        Car c1 = new Car();                      // Creating Child class object
        c1.drive();                              // Calling Overridden Method from Child class

    }

    @Override
    void drive() {                               // Overridden Method from Child class

        super.drive();                           // Calling Method from Parent class
        this.test();                             // Calling Method from Child class
        super.noTest();                          // Calling Method from Parent class
    }
}
