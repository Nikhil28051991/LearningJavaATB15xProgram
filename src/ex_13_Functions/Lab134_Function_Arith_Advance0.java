package ex_13_Functions;

import java.util.Scanner;

public class Lab134_Function_Arith_Advance0 {      // without try catch and without Exception
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


    // Function to read number
    static int readInt(Scanner scanner, String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().toLowerCase().trim();

            // Check if user entered a number
            if (isNumber(input)) {
                return convertNumber(input);
            }

            // Check if user entered number in words
            int value = wordsToNumber(input);

            if (value != -1) {
                return value;
            }

            System.out.println("Invalid input! Enter numbers only.");
        }
    }


    // Check whether input contains digits only
    static boolean isNumber(String input) {

        if (input.length() == 0) {
            return false;
        }

        int start = 0;

        // Allow negative number
        if (input.charAt(0) == '-') {
            start = 1;
        }

        if (start == input.length()) {
            return false;
        }

        for (int i = start; i < input.length(); i++) {

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
        int start = 0;
        int sign = 1;

        // Handle negative number
        if (input.charAt(0) == '-') {
            sign = -1;
            start = 1;
        }

        for (int i = start; i < input.length(); i++) {

            char ch = input.charAt(i);

            number = number * 10 + (ch - '0');
        }

        return number * sign;
    }


    // Convert number words into integer
    static int wordsToNumber(String input) {

        String[] words = input.split(" ");

        String[] numbers = {
                "zero",
                "one",
                "two",
                "three",
                "four",
                "five",
                "six",
                "seven",
                "eight",
                "nine",
                "ten"
        };

        int total = 0;

        for (int i = 0; i < words.length; i++) {

            String word = words[i];

            int value = -1;

            for (int j = 0; j < numbers.length; j++) {

                if (word.equals(numbers[j])) {
                    value = j;
                    break;
                }
            }

            // Invalid word
            if (value == -1) {
                return -1;
            }

            total = total + value;
        }

        return total;
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
