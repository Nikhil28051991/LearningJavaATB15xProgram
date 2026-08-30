package ex_15_StringBuffer_Builder_StringFunctions;

import java.util.ArrayList;
import java.util.Scanner;

public class ReverseString12 {


        // Write a Java program to reverse a given string without using any built-in reverse function.
       // 12. Using ArrayList

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Please Enter the String: ");
        String input = scanner.next();

        ArrayList<Character> characters = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            characters.add(input.charAt(i));
        }

        String reverse = "";

        for (int i = characters.size() - 1; i >= 0; i--) {
            reverse = reverse + characters.get(i);
        }

        System.out.println("Reverse String: " + reverse);
    }
}
