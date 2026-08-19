package ex_10_For_Loop;

import java.util.Scanner;

public class Task_Factorial_HR {
    public static void main(String[] args) {

        System.out.println("Wecome to the Factorial Program");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the positive int number");

        int fact = 1;

        if (scanner.hasNextInt()) {
            int n = scanner.nextInt();
            System.out.println(n);
            for (int i = 1; i <= n; i++) {
                fact = fact * i;
            }
            System.out.println("Factorial is => " + fact);
        } else {
            System.out.println("Please enter a valid int value");
        }

        scanner.close();
    }
}
