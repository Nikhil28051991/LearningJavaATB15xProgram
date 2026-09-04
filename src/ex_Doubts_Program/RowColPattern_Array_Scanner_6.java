package ex_Doubts_Program;

import java.util.Scanner;

public class RowColPattern_Array_Scanner_6 {
    public static void main(String[] args) {

        // Step 1 : Take Input
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter no. of Rows : ");
        int m = sc.nextInt();

        System.out.print("Enter no. of Columns : ");
        int n = sc.nextInt();

        // Step 2 : Create 2D Array
        int[][] numbers = new int[m][n];

        // Step 3 : Row Column Pattern Logic
        for (int r = 0; r < m; r++) {

            // Numbers Printing
            for (int c = 0; c < n; c++) {

                numbers[r][c] = r + c + 1;
            }
        }

        // Step 4 : Print Array
        System.out.println("Row Column Pattern:");

        for (int r = 0; r < m; r++) {

            // Numbers Printing
            for (int c = 0; c < n; c++) {

                System.out.print(numbers[r][c] + " ");
            }

            // Line Change
            System.out.println();
        }

        sc.close();
    }
}
