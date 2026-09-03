package ex_Doubts_Program;

import java.util.Scanner;

public class ArraySolidRectanglePattern1 {
    public static void main(String[] args) {

        // Step 1 : Take Input
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the Length : ");
        int m = sc.nextInt();

        System.out.print("Enter the Breadth : ");
        int n = sc.nextInt();


        // Step 2 : Declare and Create 2D Array
        int[][] numbers = new int[m][n];


        // Step 3 : Store values in 2D Array

        // Controls Row
        for (int r = 0; r < m; r++) {

            // Controls Column
            for (int c = 0; c < n; c++) {

                numbers[r][c] = 1;
            }
        }


        // Step 4 : Print Array as Solid Rectangle

        // Controls Row
        for (int r = 0; r < m; r++) {

            // Controls No. Of Stars in a Row
            for (int c = 0; c < n; c++) {

                System.out.print("* ");
            }

            // Line Change
            System.out.println();
        }

        sc.close();
    }
}
