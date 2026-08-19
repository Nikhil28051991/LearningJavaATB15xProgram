package ex_10_For_Loop;

import java.math.BigInteger;
import java.util.Scanner;

public class Task_Factorial_HR_1 {
    public static void main(String[] args) {

        System.out.println("Welcome to the Factorial Program");

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the positive int number");

        BigInteger fact = BigInteger.ONE;

        if (sc.hasNextInt()) {

            int n = sc.nextInt();

            System.out.println(n);

            for (int i = 1; i <= n; i++) {
                fact = fact.multiply(BigInteger.valueOf(i));
            }

            System.out.println("Factorial is => " + fact);

        } else {
            System.out.println("Please Enter a valid int Value");
        }

        sc.close();
    }
}