package ex_Doubts_Program;

public class ArrayRevRowNumRectanglePatternFixed_5 {
    public static void main(String[] args) {

        // Step 1 : Declare and Create 2D Array
        int[][] numbers = new int[3][5];


        // Step 2 : Store Reverse Row Number in Array

        // Controls Row
        for (int r = 0; r < numbers.length; r++) {

            // Controls Column
            for (int c = 0; c < numbers[r].length; c++) {

                numbers[r][c] = numbers.length - r;
            }
        }


        // Step 3 : Print Array

        // Controls Row
        for (int r = 0; r < numbers.length; r++) {

            // Controls Column
            for (int c = 0; c < numbers[r].length; c++) {

                System.out.print(numbers[r][c] + " ");
            }

            // Line Change
            System.out.println();
        }
    }
}
