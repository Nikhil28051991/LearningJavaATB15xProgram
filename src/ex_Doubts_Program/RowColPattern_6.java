package ex_Doubts_Program;

import java.util.Scanner;

public class RowColPattern_6 {
    public static void main(String[] args) {

        // Step 1 : Take Input
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter no. of Rows : ");
        int m = sc.nextInt();

        System.out.print("Enter no. of Columns : ");
        int n = sc.nextInt();

        // Step 2 : Row Column Pattern Logic
        for (int r = 1; r <= m; r++) {

            // Numbers Printing
            for (int c = r; c <= r + (n - 1); c++) {

                System.out.print(c + " ");
            }

            // Line Change
            System.out.println();
        }

        sc.close();
    }
}
