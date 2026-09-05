package ex_17_OOPs;

public class Lab161_Cats_0_OOPs {
    public static void main(String[] args) {

        Cat_0 c1 = new Cat_0();                             // Creating object using No-Argument Constructor. new Cat_0() calls Cat_0().

        Cat_0 c2 = new Cat_0("mufasa");           // Creating object using Parameterized Constructor. "mufasa" is passed to nameGiven.

        Cat_0 c3 = new Cat_0("lucy");             // Creating object using Parameterized Constructor. "lucy" is passed to nameGiven.

        Cat_0 c4 = new Cat_0("spicy");            // Creating object using Parameterized Constructor. "spicy" is passed to nameGiven.

        Cat_0 c5 = new Cat_0("oggy");             // Creating object using Parameterized Constructor. "oggy" is passed to nameGiven.


        System.out.println(c2.name);              // Accessing c2 object's name. Output: mufasa.

        System.out.println(c3.name);              // Accessing c3 object's name. Output: lucy.
    }
}


// =============================================================
// CAT_0 CLASS
// =============================================================

class Cat_0 {

    String name;                                  // Instance variable. Every Cat_0 object gets its own copy of name.


    Cat_0() {                                     // No-Argument Constructor. It is called when we use new Cat_0().
        System.out.println("DC");                 // Printing "DC". This executes when No-Argument Constructor is called.
    }


    Cat_0(String nameGiven) {                     // Parameterized Constructor. nameGiven receives the value passed during object creation.
        this.name = nameGiven;                    // this.name means current object's instance variable; nameGiven contains the value passed by us.
    }


    void running() {                              // Method used to display which Cat_0 is running.
        int local_var = 10;                       // Local variable. It exists only inside the running() method.
        System.out.println("Who is running -> " + this.name); // this.name refers to the name of the current Cat_0 object.
    }
}
