package ex_21_OOPs_Encapsulation;

public class NoEncapsulation_1 {

    public static void main(String[] args) {


// ============================================================
// Part 4: Create Object
// ============================================================

        ICICIBank_5 nikhil = new ICICIBank_5("Nikhil", 500);  // Creating ICICIBank_5 object


// ============================================================
// Part 5: Directly Access Data
// ============================================================

        System.out.println(nikhil.name5);  // Directly accessing public name5
        System.out.println(nikhil.bal5);   // Directly accessing public bal5


// ============================================================
// Part 6: Directly Modify Data
// ============================================================

        nikhil.bal5 = 1000;                // Directly modifying public bal5
        System.out.println(nikhil.bal5);   // Printing updated balance

        nikhil.name5 = "Rahul";            // Directly modifying public name5
        System.out.println(nikhil.name5);  // Printing updated name
    }
}


// ============================================================
// Part 1: Supporting Class
// ============================================================

class ICICIBank_5 {


// ============================================================
// Part 2: Public Data Members
// ============================================================

    public String name5;  // Public variable → directly accessible
    public long bal5;     // Public variable → directly accessible and modifiable


// ============================================================
// Part 3: Parameterized Constructor
// ============================================================

    public ICICIBank_5(String name, long bal) {  // Constructor with 2 arguments

        this.name5 = name;  // Assigning name parameter to name5
        this.bal5 = bal;    // Assigning balance parameter to bal5
    }
}