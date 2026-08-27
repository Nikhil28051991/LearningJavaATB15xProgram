package ex_13_Functions;

import java.util.Scanner;

public class Lab134_Function_Arith_Advance2 {   // More Suitable and Polished code  without try catch and without Exception
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Take numbers from user
        int a = readInt(scanner, "Enter the num1: ");
        int b = readInt(scanner, "Enter the num2: ");
        int c = readInt(scanner, "Enter the num3: ");

        // Calling arithmetic functions
        System.out.println("Sum = " + sum(a, b, c));
        System.out.println("Sub = " + sub(a, b, c));
        System.out.println("Mul = " + mul(a, b, c));

        if (b != 0 && c != 0) {
            System.out.println("Div = " + div(a, b, c));
            System.out.println("Mod = " + mod(a, b, c));
        } else {
            System.out.println("Cannot perform division and modulus by zero.");
        }

        scanner.close();
    }


    // Read number from user
    static int readInt(Scanner scanner, String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().toLowerCase().trim();

            // Check normal number
            if (isNumber(input)) {
                return convertNumber(input);
            }

            // Check number in words
            int value = wordToNumber(input);

            if (value != -1) {
                return value;
            }

            System.out.println("Invalid input! Enter numbers only.");
        }
    }


    // Check whether input is a number
    static boolean isNumber(String input) {

        if (input.length() == 0) {
            return false;
        }

        for (int i = 0; i < input.length(); i++) {

            char ch = input.charAt(i);

            if (ch < '0' || ch > '9') {
                return false;
            }
        }

        return true;
    }


    // Convert String number into int
    static int convertNumber(String input) {

        int number = 0;

        for (int i = 0; i < input.length(); i++) {

            char ch = input.charAt(i);

            number = number * 10 + (ch - '0');
        }

        return number;
    }


    // Convert number word into integer
    static int wordToNumber(String word) {

        String[] numbers = {
                "zero", "one", "two", "three", "four",
                "five", "six", "seven", "eight", "nine", "ten"
        };

        for (int i = 0; i < numbers.length; i++) {

            if (word.equals(numbers[i])) {
                return i;
            }
        }

        return -1;
    }


    // Addition of 3 numbers
    static int sum(int a, int b, int c) {
        return a + b + c;
    }


    // Subtraction of 3 numbers
    static int sub(int a, int b, int c) {
        return a - b - c;
    }


    // Multiplication of 3 numbers
    static int mul(int a, int b, int c) {
        return a * b * c;
    }


    // Division of 3 numbers
    static int div(int a, int b, int c) {
        return a / b / c;
    }


    // Modulus of 3 numbers
    static int mod(int a, int b, int c) {
        return a % b % c;
    }
}
