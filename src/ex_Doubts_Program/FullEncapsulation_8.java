package ex_Doubts_Program;

public class FullEncapsulation_8 {


// Part 4: Supporting Class

    static class ICICIBank_8 {

// Part 5: Private Data Members

        private String name8;  // private → name8 cannot be directly accessed outside the class
        private long bal8;     // private → bal8 cannot be directly accessed outside the class

// Part 6: Parameterized Constructor

        public ICICIBank_8(String name8, long bal8) {  // Parameterized constructor with 2 arguments

            this.name8 = name8;  // Assigning name8 parameter to name8
            this.bal8 = bal8;    // Assigning balance parameter to bal8
        }

// Part 7: Getter Methods

        public String getName8() {  // Getter method for name8
            return name8;           // Returning private name8
        }

        public long getBal8() {     // Getter method for bal8
            return bal8;            // Returning private bal8
        }


// Part 8: Setter Methods

        public void setName8(String name8) {  // Setter method for name8

            this.name8 = name8;  // Updating private name8
        }

        public void setBal8(long bal8, boolean isCashier) {  // Setter method for bal8 with permission check

            if (isCashier) {             // Checking whether the person is a cashier

                this.bal8 = bal8;        // Updating balance if cashier is true

            } else {

                System.out.println("Not allowed to modify the bal");  // Rejecting balance modification
            }
        }
    }

    public static void main(String[] args) {

// Part 9: Create Object

        ICICIBank_8 nikhil = new ICICIBank_8("Nikhil", 500);  // Creating ICICIBank_8 object

// Part 10: Read Data Using Getter

        System.out.println(nikhil.getName8());  // Reading private name8 using getter
        System.out.println(nikhil.getBal8());   // Reading private bal8 using getter

// Part 11: Modify Data Using Setter

        nikhil.setName8("Rahul");               // Modifying private name8 using setter
        nikhil.setBal8(1000, false);            // Trying to modify balance using setter

// Part 12: Print Updated Data

        System.out.println(nikhil.getName8());  // Printing updated name8
        System.out.println(nikhil.getBal8());   // Printing balance

    }
}