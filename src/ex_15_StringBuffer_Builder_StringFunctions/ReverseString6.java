package ex_15_StringBuffer_Builder_StringFunctions;

import java.util.Scanner;

public class ReverseString6 {


        // Write a Java program to reverse a given string without using any built-in reverse function.
        // 6. Using Recursion

        static void reverse(String input, int index) {

            if (index < 0) {
                return;
            }

            System.out.print(input.charAt(index));

            reverse(input, index - 1);
        }

        public static void main(String[] args) {

            Scanner scanner = new Scanner(System.in);

            System.out.print("Please Enter the String: ");
            String input = scanner.next();

            reverse(input, input.length() - 1);

    }
}
