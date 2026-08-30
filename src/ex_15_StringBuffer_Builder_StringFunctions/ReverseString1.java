package ex_15_StringBuffer_Builder_StringFunctions;

import java.util.Scanner;

public class ReverseString1 {
    public static void main(String[] args) {

       // Write a Java program to reverse a given string without using any built-in reverse function.
      // 1. Using for loop

        Scanner scanner = new Scanner(System.in);

        System.out.print("Please Enter the String: ");
        String input = scanner.next();

        String reverse = "";

        for (int i = input.length() - 1; i >= 0; i--) {
            reverse = reverse + input.charAt(i);
        }

        System.out.println("Reverse String: " + reverse);
    }
}
