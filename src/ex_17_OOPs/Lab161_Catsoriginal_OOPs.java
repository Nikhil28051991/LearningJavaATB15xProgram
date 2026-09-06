package ex_17_OOPs;

public class Lab161_Catsoriginal_OOPs {
    public static void main(String[] args) {

        CatOriginal c1 = new CatOriginal();                  // Creating object using No-Argument Constructor

        CatOriginal c2 = new CatOriginal("mufasa");         // Creating object using Parameterized Constructor

        CatOriginal c3 = new CatOriginal("lucy");            // Creating object using Parameterized Constructor

        CatOriginal c4 = new CatOriginal("spicy");           // Creating object using Parameterized Constructor

        CatOriginal c5 = new CatOriginal("oggy");            // Creating object using Parameterized Constructor

        System.out.println(c2.name);                          // Printing c2.name → mufasa

        System.out.println(c3.name);                          // Printing c3.name → lucy
    }
}


class CatOriginal {

    String name;                                               // Instance variable

    CatOriginal() {                                            // No-Argument Constructor
        System.out.println("DC");                              // Printing DC
        System.out.println(this.name);                         // Printing name → null because name is not initialized
    }

    CatOriginal(String nameGiven) {                            // Parameterized Constructor with 1 argument
        this.name = nameGiven;                                 // Assigning given name to name
    }

    void running() {                                           // Method
        int local_var = 10;                                    // Local variable
        System.out.println("Who is running -> " + this.name);  // Printing current object's name
    }
}
