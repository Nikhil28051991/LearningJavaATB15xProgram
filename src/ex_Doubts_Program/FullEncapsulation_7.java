package ex_Doubts_Program;

public class FullEncapsulation_7 {

    public static void main(String[] args) {

// Part 9: Create Object

        ICICIBank_7 nikhil = new ICICIBank_7("Nikhil", 500);  // Creating ICICIBank_6 object

// Part 10: Read Data Using Getter

        System.out.println(nikhil.getName7());  // Reading private name6 using getter
        System.out.println(nikhil.getBal7());   // Reading private bal6 using getter

// Part 11: Modify Data Using Setter

        nikhil.setName7("Rahul");               // Modifying private name6 using setter
        nikhil.setBal7(1000, false);            // Trying to modify balance using setter

// Part 12: Print Updated Data

        System.out.println(nikhil.getName7());  // Printing updated name6
        System.out.println(nikhil.getBal7());   // Printing balance

    }
}

// Part 4: Supporting Class

class ICICIBank_7 {

// Part 5: Private Data Members

    private String name7;  // private → name6 cannot be directly accessed outside the class
    private long bal7;     // private → bal6 cannot be directly accessed outside the class

// Part 6: Parameterized Constructor

    public ICICIBank_7(String name7, long bal7) {  // Parameterized constructor with 2 arguments

        this.name7 = name7;  // Assigning name parameter to name6
        this.bal7 = bal7;    // Assigning balance parameter to bal6
    }

// Part 7: Getter Methods

    public String getName7() {  // Getter method for name6
        return name7;           // Returning private name6
    }

    public long getBal7() {     // Getter method for bal6
        return bal7;            // Returning private bal6
    }


// Part 8: Setter Methods

    public void setName7(String name7) {  // Setter method for name6

        this.name7 = name7;  // Updating private name6
    }

    public void setBal7(long bal7, boolean isCashier) {  // Setter method for bal6 with permission check

        if (isCashier) {             // Checking whether the person is a cashier

            this.bal7 = bal7;        // Updating balance if cashier is true

        } else {

            System.out.println("Not allowed to modify the bal");  // Rejecting balance modification
        }
    }
}