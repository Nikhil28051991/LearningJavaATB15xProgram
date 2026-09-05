package ex_17_OOPs;

public class Lab161_Cats_1_OOPs {
    public static void main(String[] args) {

        Cat_1 c1 = new Cat_1();                  // Creating object using No-Argument Constructor. new Cat_1() calls Cat_1().

        Cat_1 c2 = new Cat_1("mufasa");         // Creating object using Parameterized Constructor. "mufasa" is passed to nameGiven.

        System.out.println(c1.name);             // Printing c1.name. Output: Kitty.

        System.out.println(c2.name);             // Printing c2.name. Output: Kitty because this constructor assigns "Kitty".
    }
}


// =============================================================
// CAT_1 CLASS
// =============================================================

class Cat_1 {

    String name;                                // Instance variable. Every Cat_1 object gets its own copy of name.


    // =========================================================
    // NO-ARGUMENT CONSTRUCTOR
    // =========================================================

    Cat_1() {                                   // No-Argument Constructor. No value is passed while creating the object.
        name = "Kitty";                         // Assigning "Kitty" to name because no name was provided.
    }


    // =========================================================
    // PARAMETERIZED CONSTRUCTOR
    // =========================================================

    Cat_1(String nameGiven) {                   // Parameterized Constructor. nameGiven receives the value passed during object creation.
        name = "Kitty";                         // Assigning "Kitty" to name. The value of nameGiven is not used.
    }


    // =========================================================
    // RUNNING METHOD
    // =========================================================

    void running() {                            // Method used to display which Cat_1 is running.

        int local_var = 10;                     // Local variable. It exists only inside the running() method.

        System.out.println("Who is running -> " + this.name); // this.name refers to the name of the current Cat_1 object.
    }
}
