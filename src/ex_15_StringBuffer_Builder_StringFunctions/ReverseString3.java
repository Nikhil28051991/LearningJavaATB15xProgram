package ex_15_StringBuffer_Builder_StringFunctions;

import java.util.Scanner;

public class ReverseString3 {
    public static void main(String[] args) {

        // Write a Java program to reverse a given string without using any built-in reverse function.
        // 3. Using do-while loop

        Scanner scanner = new Scanner(System.in);

        System.out.print("Please Enter the String: ");
        String input = scanner.next();

        String reverse = "";

        int i = input.length() - 1;

        do {
            reverse = reverse + input.charAt(i);
            i--;
        } while (i >= 0);

        System.out.println("Reverse String: " + reverse);

    }
}
