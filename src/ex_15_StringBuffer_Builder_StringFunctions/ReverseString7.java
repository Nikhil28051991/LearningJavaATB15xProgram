package ex_15_StringBuffer_Builder_StringFunctions;

import java.util.Scanner;

public class ReverseString7 {


        // Write a Java program to reverse a given string without using any built-in reverse function.
        // 7. Using Recursion and returning a String

    static String reverse(String input, int index) {

        if (index < 0) {
            return "";
        }

        return input.charAt(index) + reverse(input, index - 1);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Please Enter the String: ");
        String input = scanner.next();

        String reverse = reverse(input, input.length() - 1);

        System.out.println("Reverse String: " + reverse);

    }

}
