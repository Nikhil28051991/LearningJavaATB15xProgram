package ex_17_OOPs;

public class Lab161_Cats_OOPs {
    public static void main(String[] args) {
        // =====================================================
        // 1. USING DEFAULT / NO-ARGUMENT CONSTRUCTOR
        // =====================================================

        Cat cat1 = new Cat();   // Creating a Cat object. Since we are not passing any value, Java calls the Cat() constructor.
        // Output: DC


        // =====================================================
        // 2. USING PARAMETERIZED CONSTRUCTOR
        // =====================================================

        Cat cat2 = new Cat("Tom");   // Creating another Cat object. Here we are passing "Tom" as an argument.
        // "Tom" goes into the parameter: nameGiven.
        // Then: this.name = nameGiven;
        // This stores "Tom" inside the object's name variable.

        System.out.println(cat2.name);   // Printing the name stored inside cat2.
        // Output: Tom
    }
}


// =============================================================
// CAT CLASS
// =============================================================

class Cat {

    String name;   // Instance variable. Every Cat object gets its own 'name' variable.


    // =========================================================
    // DEFAULT / NO-ARGUMENT CONSTRUCTOR
    // =========================================================

    Cat() {   // No parameter is passed, therefore this is a No-Argument Constructor.

        System.out.println("DC");   // This statement executes automatically when new Cat() is called.
        // Output: DC
    }


    // =========================================================
    // PARAMETERIZED CONSTRUCTOR
    // =========================================================

    Cat(String nameGiven) {   // This constructor accepts one String parameter called nameGiven.

        this.name = nameGiven;   // 'this.name' = object's variable.
        // 'nameGiven' = value received from the parameter.
        // Example: new Cat("Tom")
        // Therefore: this.name = "Tom";
    }

}

