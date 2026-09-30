package ex_30_Exceptions;                                      // Package declaration

public class Lab217_Exceptions_Explained {                    // Main class

    public static void main(String[] args) {                   // Main method - program execution starts here

        System.out.println("Starting the program!");           // Prints the starting message

        String input_user = args[0];                           // Gets the first command-line argument; can cause ArrayIndexOutOfBoundsException

        Integer a = Integer.parseInt(input_user);              // Converts String input into Integer; can cause NumberFormatException

        Integer output = 100 / a;                              // Divides 100 by a; can cause ArithmeticException if a is 0

        System.out.println(output);                            // Prints the result of the division

        System.out.println("End of the program!");             // Prints the ending message if no exception occurs


        // divide by zero -> ?                                 // Question: What happens when we divide a number by zero?


        // java.lang.ArithmeticException: / by zero when args -> 0       // Occurs when command-line argument is 0

        // java.lang.NumberFormatException: For input string: "pramod" // Occurs when input is not a valid number

        // java.lang.ArrayIndexOutOfBoundsException: Index 0 out of bounds // Occurs when no command-line argument is provided

    }                                                            // End of main method

}                                                                // End of Lab217_Exceptions_Explained class