package ex_17_OOPs;

public class Elephant {

    String name;                                      // Instance variable for elephant name
    int age;                                          // Instance variable for elephant age

    Elephant(String nameGiven, int ageGiven) {       // Parameterized Constructor with 2 parameters
        this.name = nameGiven;                       // Assigning parameter value to instance variable
        this.age = ageGiven;                         // Assigning parameter value to instance variable
    }
}

class Main1 {

    public static void main(String[] args) {

        Elephant e1 = new Elephant("Raju", 25);      // Passing 2 arguments to constructor

        System.out.println(e1.name);                 // Printing elephant name
        System.out.println(e1.age);                  // Printing elephant age
    }
}
