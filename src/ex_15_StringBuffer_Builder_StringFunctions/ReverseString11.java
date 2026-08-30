package ex_15_StringBuffer_Builder_StringFunctions;

import java.util.Scanner;

public class ReverseString11 {
    public static void main(String[] args) {

        // Write a Java program to reverse a given string without using any built-in reverse function.
        // 11. Two-Pointer Swap Approach


        Scanner scanner = new Scanner(System.in);

        System.out.print("Please Enter the String: ");
        String input = scanner.next();

        char[] characters = input.toCharArray();

        int start = 0;
        int end = characters.length - 1;

        while (start < end) {

            char temp = characters[start];
            characters[start] = characters[end];
            characters[end] = temp;

            start++;
            end--;
        }

        System.out.println("Reverse String: " + new String(characters));

    }
}
