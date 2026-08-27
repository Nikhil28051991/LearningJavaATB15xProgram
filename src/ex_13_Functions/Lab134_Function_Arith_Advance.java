package ex_13_Functions;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Lab134_Function_Arith_Advance {       // with try catch, Map and HashMap
// Create a Function of Sub, Sum, Mul and Div
    // with parameter a, b and c (take the parameter from the User)

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int a = readInt(scanner, "Enter the num1: ");
        int b = readInt(scanner, "Enter the num2: ");
        int c = readInt(scanner, "Enter the num3: ");

        int result_sum = sum(a, b, c);
        int result_sub = sub(a, b, c);
        int result_mul = mul(a, b, c);
        int result_div = div(a, b, c);
        int result_mod = mod(a, b, c);

        System.out.println("Sum = " + result_sum);
        System.out.println("Sub = " + result_sub);
        System.out.println("Mul = " + result_mul);
        System.out.println("Div = " + result_div);
        System.out.println("Mod = " + result_mod);

        scanner.close();
    }

    // Read number from user
    // Accepts:
    // 10
    // Four
    // six
    // fifty five
    // One hundred twenty five
    //
    // Rejects:
    // Nikhil
    // Sonawane
    // Rahul
    // Hello

    static int readInt(Scanner scanner, String prompt) {

        while (true) {

            System.out.print(prompt);

            String input = scanner.nextLine().trim();

            // If user enters a normal numeric value
            if (input.matches("-?\\d+")) {
                return Integer.parseInt(input);
            }

            // Try to convert number words into integer
            try {
                return convertWordsToNumber(input);
            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid input! Please enter numbers only " +
                                "(example: 10, Four, Six, Fifty Five)."
                );
            }
        }
    }

    // Convert number words into integer
    static int convertWordsToNumber(String input) {

        Map<String, Integer> numbers = new HashMap<>();

        numbers.put("zero", 0);
        numbers.put("one", 1);
        numbers.put("two", 2);
        numbers.put("three", 3);
        numbers.put("four", 4);
        numbers.put("five", 5);
        numbers.put("six", 6);
        numbers.put("seven", 7);
        numbers.put("eight", 8);
        numbers.put("nine", 9);
        numbers.put("ten", 10);
        numbers.put("eleven", 11);
        numbers.put("twelve", 12);
        numbers.put("thirteen", 13);
        numbers.put("fourteen", 14);
        numbers.put("fifteen", 15);
        numbers.put("sixteen", 16);
        numbers.put("seventeen", 17);
        numbers.put("eighteen", 18);
        numbers.put("nineteen", 19);

        numbers.put("twenty", 20);
        numbers.put("thirty", 30);
        numbers.put("forty", 40);
        numbers.put("fifty", 50);
        numbers.put("sixty", 60);
        numbers.put("seventy", 70);
        numbers.put("eighty", 80);
        numbers.put("ninety", 90);

        numbers.put("hundred", 100);
        numbers.put("thousand", 1000);

        String[] words = input.toLowerCase().split("\\s+");

        int total = 0;
        int current = 0;

        for (String word : words) {

            // Ignore "and"
            if (word.equals("and")) {
                continue;
            }

            if (!numbers.containsKey(word)) {
                throw new IllegalArgumentException();
            }

            int value = numbers.get(word);

            if (value == 100) {

                if (current == 0) {
                    current = 1;
                }

                current = current * 100;

            } else if (value == 1000) {

                if (current == 0) {
                    current = 1;
                }

                total = total + (current * 1000);
                current = 0;

            } else {

                current = current + value;
            }
        }

        total = total + current;

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

        if (b == 0 || c == 0) {
            throw new ArithmeticException(
                    "Division by zero is not allowed."
            );
        }

        return a / b / c;
    }

    // Modulus of 3 numbers
    static int mod(int a, int b, int c) {

        if (b == 0 || c == 0) {
            throw new ArithmeticException(
                    "Modulo by zero is not allowed."
            );
        }

        return a % b % c;
    }


}
