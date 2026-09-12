package ex_21_OOPs_Encapsulation;

public class FullEncapsulation {

    public static void main(String[] args) {

        ICICIBank2 amit = new ICICIBank2("Amit", 100);       // Creating object of ICICIBank2 using Parameterized Constructor

        long bal2 = amit.getBal2();                         // Calling getter method to READ the private balance

        System.out.println(bal2);                           // Printing Amit's balance → 100


        // System.out.println(amit.bal2);                   // ❌ Not allowed because bal2 is private


        amit.setBal2(200, false);                           // Calling setter to MODIFY balance, false means Amit is not a cashier

        System.out.println(amit.getBal2());                // Reading balance using getter → 100 because modification was not allowed


        ICICIBank2 cashier = new ICICIBank2("Cash", 100);  // Creating another ICICIBank2 object for Cashier

        cashier.setBal2(200, true);                        // Calling setter, true means Cashier is allowed to modify balance

        System.out.println(cashier.getBal2());             // Reading updated balance using getter → 200
    }
}


class ICICIBank2 {

    private String name2;                                  // private → Data is hidden and cannot be directly accessed outside this class

    private long bal2;                                     // private → Balance is protected from direct access


    public String getName2() {                             // Getter method → Used to READ the private name

        return name2;                                      // Returning the value of name2
    }


    public void setName2(String name2) {                   // Setter method → Used to MODIFY the private name

        this.name2 = name2;                                // this.name2 is instance variable and name2 is the parameter
    }


    public long getBal2() {                                // Getter method → Used to READ the private balance

        return bal2;                                       // Returning the value of bal2
    }


    public void setBal2(long bal2, boolean isCashier) {    // Setter method → Used to MODIFY balance with a security condition

        if (isCashier) {                                   // Checking whether the person is a cashier

            this.bal2 = bal2;                              // If cashier is true, balance modification is allowed

        } else {

            System.out.println("Not allowed to modify the bal"); // If cashier is false, balance modification is rejected
        }
    }


    public ICICIBank2(String name2, long bal2) {            // Parameterized Constructor with 2 arguments

        this.name2 = name2;                                // Assigning constructor parameter name2 to private instance variable

        this.bal2 = bal2;                                  // Assigning constructor parameter bal2 to private instance variable
    }

}
