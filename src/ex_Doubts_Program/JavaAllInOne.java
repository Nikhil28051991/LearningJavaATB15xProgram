package ex_Doubts_Program;

public class JavaAllInOne {

    public static void main(String[] args) {


        // ============================================================
        // 1. VARIABLES AND DATA TYPES
        // ============================================================

        int age = 25;                              // int stores whole numbers
        double salary = 45000.50;                 // double stores decimal numbers
        char grade = 'A';                         // char stores a single character
        boolean isStudent = true;                 // boolean stores true or false
        String name = "Nikhil";                   // String stores text

        System.out.println("Name: " + name);      // Printing String value
        System.out.println("Age: " + age);        // Printing int value
        System.out.println("Salary: " + salary);  // Printing double value
        System.out.println("Grade: " + grade);    // Printing char value
        System.out.println("Student: " + isStudent); // Printing boolean value



        // ============================================================
        // 2. LITERALS
        // ============================================================

        int number = 100;                         // 100 is an integer literal
        double price = 99.99;                    // 99.99 is a floating-point literal
        char letter = 'N';                       // 'N' is a character literal
        String city = "Mumbai";                  // "Mumbai" is a String literal
        boolean result = true;                   // true is a boolean literal

        System.out.println(number);              // Printing integer literal value
        System.out.println(price);               // Printing decimal literal value
        System.out.println(letter);              // Printing character literal value
        System.out.println(city);                // Printing String literal value
        System.out.println(result);              // Printing boolean literal value



        // ============================================================
        // 3. ARITHMETIC OPERATORS
        // ============================================================

        int a = 10;                               // First number
        int b = 3;                                // Second number

        System.out.println(a + b);                // Addition → 13
        System.out.println(a - b);                // Subtraction → 7
        System.out.println(a * b);                // Multiplication → 30
        System.out.println(a / b);                // Division → 3
        System.out.println(a % b);                // Modulus → 1



        // ============================================================
        // 4. RELATIONAL OPERATORS
        // ============================================================

        System.out.println(a > b);                // Greater than → true
        System.out.println(a < b);                // Less than → false
        System.out.println(a >= b);               // Greater than or equal → true
        System.out.println(a <= b);               // Less than or equal → false
        System.out.println(a == b);               // Equal → false
        System.out.println(a != b);               // Not equal → true



        // ============================================================
        // 5. LOGICAL OPERATORS
        // ============================================================

        int marks = 75;                            // Student marks

        System.out.println(marks >= 40 && marks <= 100); // AND → both conditions must be true
        System.out.println(marks < 40 || marks > 90);    // OR → at least one condition must be true
        System.out.println(!(marks < 40));               // NOT → reverses the result



        // ============================================================
        // 6. ASSIGNMENT OPERATORS
        // ============================================================

        int x = 10;                                // Assigning 10 to x

        x += 5;                                    // x = x + 5 → 15
        x -= 2;                                    // x = x - 2 → 13
        x *= 2;                                    // x = x * 2 → 26
        x /= 2;                                    // x = x / 2 → 13

        System.out.println(x);                     // Printing final value of x



        // ============================================================
        // 7. TYPE CASTING
        // ============================================================

        double decimalNumber = 10.75;              // double value

        int convertedNumber = (int) decimalNumber; // Explicit casting → double to int

        System.out.println(convertedNumber);       // Output → 10



        // ============================================================
        // 8. IMPLICIT / WIDENING TYPE CASTING
        // ============================================================

        int smallNumber = 100;                     // int value

        double bigNumber = smallNumber;            // int automatically converted to double

        System.out.println(bigNumber);             // Output → 100.0



        // ============================================================
        // 9. TERNARY OPERATOR
        // ============================================================

        int ageCheck = 20;                         // Age value

        String votingStatus = ageCheck >= 18 ? "Eligible" : "Not Eligible"; // Ternary operator

        System.out.println(votingStatus);          // Output → Eligible



        // ============================================================
        // 10. INCREMENT AND DECREMENT OPERATORS
        // ============================================================

        int count = 10;                            // Starting value

        count++;                                   // Increment → 11
        count--;                                   // Decrement → 10

        ++count;                                   // Pre-increment → 11
        --count;                                   // Pre-decrement → 10

        System.out.println(count);                // Printing count



        // ============================================================
        // 11. IF-ELSE CONDITION
        // ============================================================

        int studentMarks = 65;                    // Student marks

        if (studentMarks >= 40) {                 // Checking whether marks are 40 or above
            System.out.println("Pass");           // Executes when condition is true
        } else {
            System.out.println("Fail");           // Executes when condition is false
        }



        // ============================================================
        // 12. ELSE-IF CONDITION
        // ============================================================

        int score = 85;                           // Student score

        if (score >= 90) {                        // Checking for A grade
            System.out.println("Grade A+");
        } else if (score >= 75) {                 // Checking for A grade
            System.out.println("Grade A");
        } else if (score >= 60) {                 // Checking for B grade
            System.out.println("Grade B");
        } else {
            System.out.println("Grade C");       // If all above conditions are false
        }



        // ============================================================
        // 13. SWITCH STATEMENT
        // ============================================================

        int day = 2;                              // Day number

        switch (day) {                            // Switch checks the value of day

            case 1:
                System.out.println("Monday");     // Executes when day = 1
                break;

            case 2:
                System.out.println("Tuesday");    // Executes when day = 2
                break;

            case 3:
                System.out.println("Wednesday");  // Executes when day = 3
                break;

            default:
                System.out.println("Invalid Day"); // Executes when no case matches
        }



        // ============================================================
        // 14. FOR LOOP
        // ============================================================

        for (int i = 1; i <= 5; i++) {             // Start → condition → increment
            System.out.println("For: " + i);       // Prints 1 to 5
        }



        // ============================================================
        // 15. WHILE LOOP
        // ============================================================

        int i = 1;                                 // Starting value

        while (i <= 5) {                           // Loop runs while condition is true
            System.out.println("While: " + i);    // Printing value
            i++;                                   // Increasing i
        }



        // ============================================================
        // 16. DO-WHILE LOOP
        // ============================================================

        int j = 1;                                 // Starting value

        do {
            System.out.println("Do While: " + j); // Executes at least once
            j++;                                   // Increasing j
        } while (j <= 5);                           // Condition checked after execution



        // ============================================================
        // 17. BREAK
        // ============================================================

        for (int k = 1; k <= 10; k++) {            // Loop from 1 to 10

            if (k == 5) {                          // Checking whether k is 5
                break;                             // Completely stops the loop
            }

            System.out.println(k);                // Prints 1 to 4
        }



        // ============================================================
        // 18. CONTINUE
        // ============================================================

        for (int k = 1; k <= 5; k++) {             // Loop from 1 to 5

            if (k == 3) {                          // Checking whether k is 3
                continue;                          // Skips current iteration
            }

            System.out.println(k);                // Prints 1, 2, 4, 5
        }



        // ============================================================
        // 19. METHODS / FUNCTIONS
        // ============================================================

        int additionResult = add(10, 20);          // Calling add() method and storing returned value

        System.out.println(additionResult);        // Printing result → 30



        // ============================================================
        // 20. STRING
        // ============================================================

        String firstName = "Nikhil";               // Creating String

        System.out.println(firstName.length());   // length() → number of characters

        System.out.println(firstName.toUpperCase()); // Converts String to uppercase

        System.out.println(firstName.toLowerCase()); // Converts String to lowercase

        System.out.println(firstName.charAt(0));  // charAt(0) → first character

        System.out.println(firstName.contains("Nikhil")); // contains() checks text

        System.out.println(firstName.equals("Nikhil"));   // equals() compares Strings



        // ============================================================
        // 21. STRING CONCATENATION
        // ============================================================

        String first = "Nikhil";                   // First String
        String last = "Sonawane";                  // Second String

        String fullName = first + " " + last;      // Joining two Strings

        System.out.println(fullName);              // Output → Nikhil Sonawane



        // ============================================================
        // 22. 1D ARRAY
        // ============================================================

        int[] numbers = {10, 20, 30, 40, 50};     // Creating and initializing 1D array

        System.out.println(numbers[0]);           // First element → 10
        System.out.println(numbers[2]);           // Third element → 30

        System.out.println(numbers.length);       // Array size → 5



        // ============================================================
        // 23. 1D ARRAY USING FOR LOOP
        // ============================================================

        for (int index = 0; index < numbers.length; index++) { // Loop through array
            System.out.println(numbers[index]);              // Printing each element
        }



        // ============================================================
        // 24. ENHANCED FOR LOOP / FOR-EACH
        // ============================================================

        for (int value : numbers) {                // value gets each array element
            System.out.println(value);             // Printing each element
        }



        // ============================================================
        // 25. 2D ARRAY
        // ============================================================

        int[][] matrix = {                         // Creating 2D array

                {10, 20, 30},                      // Row 0
                {40, 50, 60},                      // Row 1
                {70, 80, 90}                       // Row 2
        };

        System.out.println(matrix[0][0]);          // Row 0, Column 0 → 10
        System.out.println(matrix[1][2]);          // Row 1, Column 2 → 60



        // ============================================================
        // 26. 2D ARRAY USING NESTED FOR LOOP
        // ============================================================

        for (int row = 0; row < matrix.length; row++) {       // Outer loop handles rows

            for (int column = 0; column < matrix[row].length; column++) { // Inner loop handles columns

                System.out.print(matrix[row][column] + " ");  // Printing each element
            }

            System.out.println();                              // Moving to next row
        }



        // ============================================================
        // 27. 3D ARRAY
        // ============================================================

        int[][][] threeD = {

                {
                        {10, 20, 30},              // Layer 0, Row 0
                        {40, 50, 60}               // Layer 0, Row 1
                },

                {
                        {70, 80, 90},              // Layer 1, Row 0
                        {100, 110, 120}            // Layer 1, Row 1
                }
        };

        System.out.println(threeD[0][0][0]);       // Layer 0, Row 0, Column 0 → 10
        System.out.println(threeD[1][1][2]);       // Layer 1, Row 1, Column 2 → 120



        // ============================================================
        // 28. OBJECT CREATION
        // ============================================================

        JavaAllInOne obj1 = new JavaAllInOne();     // Creating object of current class



        // ============================================================
        // 29. CONSTRUCTOR
        // ============================================================

        JavaAllInOne obj2 = new JavaAllInOne("Nikhil"); // Calling parameterized constructor

        System.out.println(obj2.personName);        // Printing value initialized by constructor



        // ============================================================
        // 30. METHOD OVERLOADING
        // ============================================================

        int result1 = obj1.add(10, 20);             // Calls add(int, int)

        int result2 = obj1.add(10, 20, 30);         // Calls add(int, int, int)

        double result3 = obj1.add(10.5, 20.5);      // Calls add(double, double)

        System.out.println(result1);                // Output → 30

        System.out.println(result2);                // Output → 60

        System.out.println(result3);                // Output → 31.0
    }


    // ================================================================
    // METHOD / FUNCTION
    // ================================================================

    static int add(int a, int b) {                  // Method with 2 int parameters
        return a + b;                               // Returning addition result
    }


    // ================================================================
    // CONSTRUCTOR
    // ================================================================

    String personName;                              // Instance variable


    JavaAllInOne() {                                // No-Argument Constructor
        System.out.println("No-Argument Constructor"); // Constructor executes when object is created
    }


    JavaAllInOne(String nameGiven) {                // Parameterized Constructor with 1 parameter
        this.personName = nameGiven;                // this refers to current object's variable
    }


    // ================================================================
    // METHOD OVERLOADING
    // ================================================================

    int add(int a, int b, int c) {                  // Overloaded method with 3 int parameters
        return a + b + c;                           // Returning addition result
    }


    double add(double a, double b) {                // Overloaded method with 2 double parameters
        return a + b;                               // Returning addition result
    }

}
