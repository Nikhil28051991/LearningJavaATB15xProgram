package ex_15_StringBuffer_Builder_StringFunctions;

import java.util.Scanner;
import java.util.stream.IntStream;

public class ReverseString14 {
    public static void main(String[] args) {

        // Write a Java program to reverse a given string without using any built-in reverse function.
        // 14. Using Java Stream API

        Scanner scanner = new Scanner(System.in);

        System.out.print("Please Enter the String: ");
        String input = scanner.next();

        String reverse = IntStream
                .range(0, input.length())
                .mapToObj(i -> input.charAt(input.length() - 1 - i))
                .collect(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append
                )
                .toString();

        System.out.println("Reverse String: " + reverse);
    }
}
