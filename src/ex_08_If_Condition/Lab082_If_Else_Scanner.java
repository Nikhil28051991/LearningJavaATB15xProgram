package ex_08_If_Condition;

import java.util.Scanner;

public class Lab082_If_Else_Scanner {
    // Allowed to vote or not - age
    // If age > 18 -> allowed to vote.
    // else age < >18 -> Not allowed to vote.

    // How to take the user Input
    // 1. CLI Options
    //   int age = Integer.parseInt(args[0]);
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the age");

        int age= sc.nextInt();
        // double d = sc.nextDouble();
        // float f = sc.nextFloat();
        System.out.println(age);

        if (age > 19) {
            System.out.println("Hi");
        }



    }
}
