package ex_Doubts_Program;

public class ArraySolidRectanglePatternFixed1 {
    public static void main(String[] args) {

        // Step 1 : Declare and Initialize 2D Array
        int[][] numbers = new int[3][5];


        // Step 2 : Store values in 2D Array

        // Controls Row
        for (int r = 0; r < numbers.length; r++) {

            // Controls Column
            for (int c = 0; c < numbers[r].length; c++) {

                numbers[r][c] = 1;
            }
        }


        // Step 3 : Print Array as Solid Rectangle

        // Controls Row
        for (int r = 0; r < numbers.length; r++) {

            // Controls No. Of Stars in a Row
            for (int c = 0; c < numbers[r].length; c++) {

                System.out.print("* ");
            }

            // Line Change
            System.out.println();
        }
    }
}
