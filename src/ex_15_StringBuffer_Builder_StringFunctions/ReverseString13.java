package ex_15_StringBuffer_Builder_StringFunctions;

import java.util.Scanner;

public class ReverseString13 {
    public static void main(String[] args) {

        // Write a Java program to reverse a given string without using any built-in reverse function.
        // 13. Using Byte Array

        Scanner scanner = new Scanner(System.in);

        System.out.print("Please Enter the String: ");
        String input = scanner.next();

        byte[] characters = input.getBytes();

        for (int i = characters.length - 1; i >= 0; i--) {
            System.out.print((char) characters[i]);
        }

    }
}
