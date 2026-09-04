package ex_Doubts_Program;

public class RowColPattern_Array_6 {
    public static void main(String[] args) {

        // Step 1 : Create and Initialize 2D Array
        int[][] numbers = {
                {1, 2, 3, 4, 5},
                {2, 3, 4, 5, 6},
                {3, 4, 5, 6, 7},
                {4, 5, 6, 7, 8}
        };

        // Step 2 : Row Column Pattern Logic
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
