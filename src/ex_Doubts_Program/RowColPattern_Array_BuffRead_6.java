package ex_Doubts_Program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RowColPattern_Array_BuffRead_6 {
    public static void main(String[] args) throws IOException, IOException {

        // Step 1 : Take Input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter no. of Rows : ");
        int m = Integer.parseInt(br.readLine());

        System.out.print("Enter no. of Columns : ");
        int n = Integer.parseInt(br.readLine());

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

        for (int r = 0; r < numbers.length; r++) {

            // Numbers Printing
            for (int c = 0; c < numbers[r].length; c++) {

                System.out.print(numbers[r][c] + " ");
            }

            // Line Change
            System.out.println();
        }
    }

}
