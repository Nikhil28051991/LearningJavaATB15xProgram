package ex_15_StringBuffer_Builder_StringFunctions;

import java.util.Scanner;
import java.util.Stack;

public class ReverseString9 {
    public static void main(String[] args) {

        // Write a Java program to reverse a given string without using any built-in reverse function.
        // 9. Using a Stack

        Scanner scanner = new Scanner(System.in);

        System.out.print("Please Enter the String: ");
        String input = scanner.next();

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < input.length(); i++) {
            stack.push(input.charAt(i));
        }

        String reverse = "";

        while (!stack.isEmpty()) {
            reverse = reverse + stack.pop();
        }

        System.out.println("Reverse String: " + reverse);

    }
}
