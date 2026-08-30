package ex_15_StringBuffer_Builder_StringFunctions;

import java.util.Scanner;

public class ReverseString4 {
    public static void main(String[] args) {

        // Write a Java program to reverse a given string without using any built-in reverse function.
        // 4. Using a char[] array

        Scanner scanner = new Scanner(System.in);

        System.out.print("Please Enter the String: ");
        String input = scanner.next();

        char[] characters = input.toCharArray();

        for (int i = characters.length - 1; i >= 0; i--) {
            System.out.print(characters[i]);
        }


    }
}
