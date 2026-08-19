package ex_10_For_Loop;

import java.util.Scanner;

public class Lab112_For_loop_Continue {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a no.");

        int num = sc.nextInt();

        for (int i = 0; i<num;i++) {

             if (i == 5) {   // It will not print 5 It will skip it and then go to for loop and print remaining no till 49 because we entered 50
                 continue;
             }
            System.out.println(i);
        }

    }
}
