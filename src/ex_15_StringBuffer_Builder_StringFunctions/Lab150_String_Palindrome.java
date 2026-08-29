package ex_15_StringBuffer_Builder_StringFunctions;

import java.util.Scanner;

public class Lab150_String_Palindrome {
    public static void main(String[] args) {

        //  madam -> reverse -> madam

        Scanner scanner = new Scanner(System.in);
        System.out.println("ENTER THE STRING!");
        String user_input = scanner.next();

        String reversed_string = "";

        for (int i = user_input.length() - 1; i >= 0; i--) {
            reversed_string = reversed_string + user_input.charAt(i);
        }

        if (reversed_string.equalsIgnoreCase(user_input)) {
            System.out.println("Palindrome");
        } else {
            System.out.println("Not a Palindrome!");

        }
    }
}
