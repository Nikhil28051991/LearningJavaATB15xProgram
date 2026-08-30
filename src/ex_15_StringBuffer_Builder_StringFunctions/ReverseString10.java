package ex_15_StringBuffer_Builder_StringFunctions;

import java.util.Scanner;

public class ReverseString10 {
    public static void main(String[] args) {

        // Write a Java program to reverse a given string without using any built-in reverse function.
        // 10. Using StringBuffer manually

        Scanner scanner = new Scanner(System.in);

        System.out.print("Please Enter the String: ");
        String input = scanner.next();

        StringBuffer reverse = new StringBuffer();

        for (int i = input.length() - 1; i >= 0; i--) {
            reverse.append(input.charAt(i));
        }

        System.out.println("Reverse String: " + reverse);


    }
}
