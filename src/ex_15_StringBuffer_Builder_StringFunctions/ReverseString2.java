package ex_15_StringBuffer_Builder_StringFunctions;

import java.util.Scanner;

public class ReverseString2 {
    public static void main(String[] args) {

        // Write a Java program to reverse a given string without using any built-in reverse function.
        // 2. Using while loop

        Scanner scanner = new Scanner(System.in);

        System.out.print("Please Enter the String: ");
        String input = scanner.next();

        String reverse = "";

        int i = input.length() - 1;

        while (i >= 0) {
            reverse = reverse + input.charAt(i);
            i--;
        }

        System.out.println("Reverse String: " + reverse);


    }
}
