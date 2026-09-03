package ex_Doubts_Program;

import java.util.Scanner;

public class ArrayRevColNumRectanglePattern_3 {
    public static void main(String[] args) {

        // Step 1 : Take Input
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter no. of Rows : ");
        int m = sc.nextInt();

        System.out.print("Enter no. of Columns : ");
        int n = sc.nextInt();


        // Step 2 : Declare and Create 2D Array
        int[][] numbers = new int[m][n];


        // Step 3 : Store Reverse Column Number in Array

        // Controls Row
        for (int r = 0; r < m; r++) {

            // Controls Column
            for (int c = 0; c < n; c++) {

                numbers[r][c] = n - c;
            }
        }


        // Step 4 : Print Array

        // Controls Row
        for (int r = 0; r < m; r++) {

            // Controls Column
            for (int c = 0; c < n; c++) {

                System.out.print(numbers[r][c] + " ");
            }

            // Line Change
            System.out.println();
        }

        sc.close();
    }
}
