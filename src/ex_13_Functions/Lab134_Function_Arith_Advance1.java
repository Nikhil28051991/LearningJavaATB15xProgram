package ex_13_Functions;

import java.util.Scanner;

public class Lab134_Function_Arith_Advance1 {
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
        System.out.println("Div = " + div(a, b, c));
        System.out.println("Mod = " + mod(a, b, c));

        scanner.close();
    }

    // Read number from user
    // Accepts: 10, Four, Six, Fifty Five
    // Rejects: Nikhil, Rahul, Hello
    static int readInt(Scanner scanner, String message) {

        while (true) {

            System.out.print(message);
            String input = scanner.nextLine().trim();

            // If user enters a normal number: 10, 25, 100
            if (input.matches("-?\\d+")) {
                return Integer.parseInt(input);
            }

            // If user enters number in words
            try {
                return wordsToNumber(input);
            } catch (Exception e) {
                System.out.println("Invalid! Enter numbers only.");
            }
        }
    }

    // Convert number words into integer
    static int wordsToNumber(String input) throws Exception {

        String[] words = input.toLowerCase().split("\\s+");

        String[] ones = {
                "zero", "one", "two", "three", "four",
                "five", "six", "seven", "eight", "nine",
                "ten", "eleven", "twelve", "thirteen", "fourteen",
                "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
        };

        String[] tens = {
                "", "", "twenty", "thirty", "forty",
                "fifty", "sixty", "seventy", "eighty", "ninety"
        };

        int total = 0;
        int current = 0;

        for (String word : words) {

            // Ignore "and"
            if (word.equals("and"))
                continue;

            int value = -1;

            // Check 0 to 19
            for (int i = 0; i < ones.length; i++) {
                if (word.equals(ones[i])) {
                    value = i;
                    break;
                }
            }

            // Check 20, 30, 40...90
            if (value == -1) {
                for (int i = 2; i < tens.length; i++) {
                    if (word.equals(tens[i])) {
                        value = i * 10;
                        break;
                    }
                }
            }

            // Invalid word = name or other text
            if (value == -1 && !word.equals("hundred")
                    && !word.equals("thousand")) {
                throw new Exception();
            }

            // Handle hundred
            if (word.equals("hundred")) {
                current = (current == 0 ? 1 : current) * 100;
            }

            // Handle thousand
            else if (word.equals("thousand")) {
                total += (current == 0 ? 1 : current) * 1000;
                current = 0;
            }

            else {
                current += value;
            }
        }

        return total + current;
    }

    // Arithmetic functions for 3 numbers
    static int sum(int a, int b, int c) {
        return a + b + c;
    }

    static int sub(int a, int b, int c) {
        return a - b - c;
    }

    static int mul(int a, int b, int c) {
        return a * b * c;
    }

    static int div(int a, int b, int c) {
        if (b == 0 || c == 0)
            throw new ArithmeticException("Cannot divide by zero.");

        return a / b / c;
    }

    static int mod(int a, int b, int c) {
        if (b == 0 || c == 0)
            throw new ArithmeticException("Cannot use zero for modulus.");

        return a % b % c;
    }

}
