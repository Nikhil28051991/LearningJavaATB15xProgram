package ex_21_OOPs_Encapsulation;

public class FullEncapsulation_6 {

    public static void main(String[] args) {


// ============================================================
// Part 6: Create Object
// ============================================================

        ICICIBank_6 nikhil = new ICICIBank_6("Nikhil", 500);  // Creating ICICIBank_6 object


// ============================================================
// Part 7: Read Data Using Getter
// ============================================================

        System.out.println(nikhil.getName6());  // Reading private name6 using getter
        System.out.println(nikhil.getBal6());   // Reading private bal6 using getter


// ============================================================
// Part 8: Modify Data Using Setter
// ============================================================

        nikhil.setName6("Rahul");               // Modifying private name6 using setter
        nikhil.setBal6(1000, false);            // Trying to modify balance using setter


// ============================================================
// Part 9: Print Updated Data
// ============================================================

        System.out.println(nikhil.getName6());  // Printing updated name6
        System.out.println(nikhil.getBal6());   // Printing balance
    }
}


// ============================================================
// Part 1: Supporting Class
// ============================================================

class ICICIBank_6 {


// ============================================================
// Part 2: Private Data Members
// ============================================================

    private String name6;  // private → name6 cannot be directly accessed outside the class
    private long bal6;     // private → bal6 cannot be directly accessed outside the class


// ============================================================
// Part 3: Parameterized Constructor
// ============================================================

    public ICICIBank_6(String name6, long bal6) {  // Parameterized constructor with 2 arguments

        this.name6 = name6;  // Assigning name parameter to name6
        this.bal6 = bal6;    // Assigning balance parameter to bal6
    }


// ============================================================
// Part 4: Getter Methods
// ============================================================

    public String getName6() {  // Getter method for name6
        return name6;           // Returning private name6
    }

    public long getBal6() {     // Getter method for bal6
        return bal6;            // Returning private bal6
    }


// ============================================================
// Part 5: Setter Methods
// ============================================================

    public void setName6(String name6) {  // Setter method for name6

        this.name6 = name6;  // Updating private name6
    }

    public void setBal6(long bal6, boolean isCashier) {  // Setter method for bal6 with permission check

        if (isCashier) {             // Checking whether the person is a cashier

            this.bal6 = bal6;        // Updating balance if cashier is true

        } else {

            System.out.println("Not allowed to modify the bal");  // Rejecting balance modification
        }
    }
}