package ex_Doubts_Program;

import java.util.Scanner;

public class SolidRectanglePattern {
    public static void main(String[] args) {

          // Step 1 : Take Input
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Length : ");
        int m = sc.nextInt();
        System.out.print("Enter the Breadth : ");
        int n = sc.nextInt();

        // Step 2 : Nested Loop For Patterns

        //Controls Row
        for (int r = 1; r <= m; r++) {

            //Controls No. Of Stars in a Row
            for (int c = 1; c <= n; c++) {
                System.out.print("* ");
            }
            // Line Change
            System.out.println();

        }
        sc.close();
    }
}
