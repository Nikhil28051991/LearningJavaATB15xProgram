package ex_Doubts_Program;

import java.util.Scanner;

public class RowNumRectanglePattern {
    public static void main(String[] args) {

        // Step 1 : Take Input
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter no. of Rows : ");
        int m = sc.nextInt();

        System.out.print("Enter no. of Columns : ");
        int n = sc.nextInt();

        // Step 2 : Row Number Rectangle Pattern Logic
        for (int r = 1; r <= m; r++) {

            // Column Loop
            for (int c = 1; c <= n; c++) {

                System.out.print(r + " ");
            }

            // Line Change
            System.out.println();
        }
    }

}
