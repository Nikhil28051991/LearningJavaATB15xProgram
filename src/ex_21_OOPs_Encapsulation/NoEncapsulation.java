package ex_21_OOPs_Encapsulation;

public class NoEncapsulation {

    public static void main(String[] args) {

        ICICIBank1 amit = new ICICIBank1("Amit", 100);       // Creating object of ICICIBank and passing name and balance

        System.out.println(amit.name1);                    // Directly accessing name because it is public

        System.out.println(amit.bal1);                     // Directly accessing balance because it is public

        amit.bal1 = 200;                                   // Directly modifying balance because bal is public

        System.out.println(amit.bal1);                     // Printing updated balance

        amit.name1 = "Rahul";                              // Directly modifying name because name is public

        System.out.println(amit.name1);                    // Printing updated name
    }
}


class ICICIBank1 {

    public String name1;                                   // public → Data can be directly accessed from outside the class

    public long bal1;                                      // public → Balance can be directly accessed and modified


    public ICICIBank1(String name, long bal) {              // Parameterized Constructor with 2 arguments

        this.name1 = name1;                                 // this.name → instance variable, name → constructor parameter

        this.bal1 = bal1;                                   // this.bal → instance variable, bal → constructor parameter
    }

}
