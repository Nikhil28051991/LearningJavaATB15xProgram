package ex_Doubts_Program;

import java.util.Scanner;

public class ColNumRevRectanglePattern {
    public static void main(String[] args) {

        // Step 1 : Take Input
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter no. of Rows : ");
        int m = sc.nextInt();

        System.out.print("Enter no. of Columns : ");
        int n = sc.nextInt();

        // Step 2 : Column Number Reverse Rectangle Pattern Logic
        for (int r = 1; r <= m; r++) {

            // Column Loop
            for (int c = n; c >= 1; c--) {

                System.out.print(c + " ");
            }

            // Line Change
            System.out.println();
        }
    }
}
