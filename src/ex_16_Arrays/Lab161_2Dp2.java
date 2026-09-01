package ex_16_Arrays;

import java.util.Arrays;

public class Lab161_2Dp2 {
    public static void main(String[] args) {

        // =========================================================
        // 2D ARRAY INITIALIZATION
        // =========================================================

        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] matrix_2_2 = {
                {1, 2},
                {3, 4}
        };

        int[][] matrix_3_1 = {
                {1},
                {3},
                {5}
        };


        // =========================================================
        // PART 1
        // ARRAY FORMAT PRINTING
        // =========================================================

        // =========================================================
        // PRINTING METHOD 1: Arrays.deepToString()
        // =========================================================
        // Easiest way to print a complete 2D array.
        //
        // deepToString() automatically creates:
        // [ ] around each row
        // commas between elements and rows

        System.out.println("\n=========================================================");
        System.out.println("PRINTING METHOD 1: Arrays.deepToString()");
        System.out.println("=========================================================");

        System.out.println("\nMatrix - Direct Initialization:");

        System.out.println(Arrays.deepToString(matrix));
        System.out.println(Arrays.deepToString(matrix_2_2));
        System.out.println(Arrays.deepToString(matrix_3_1));


        // =========================================================
        // PRINTING METHOD 2: NESTED FOR LOOP
        // =========================================================
        // Normally nested for loop prints a 2D array like:
        //
        // 1 2 3
        // 4 5 6
        // 7 8 9
        //
        // Here we manually add [ ] and commas
        // to produce the same format as deepToString().

        System.out.println("\n=========================================================");
        System.out.println("PRINTING METHOD 2: NESTED FOR LOOP");
        System.out.println("=========================================================");

        System.out.println("\nMatrix - Direct Initialization:");

        // -------------------- matrix --------------------

        System.out.print("[");

        for (int i = 0; i < matrix.length; i++) {

            System.out.print("[");

            for (int j = 0; j < matrix[i].length; j++) {

                System.out.print(matrix[i][j]);

                if (j < matrix[i].length - 1) {
                    System.out.print(", ");
                }
            }

            System.out.print("]");

            if (i < matrix.length - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");


        // -------------------- matrix_2_2 --------------------

        System.out.print("[");

        for (int i = 0; i < matrix_2_2.length; i++) {

            System.out.print("[");

            for (int j = 0; j < matrix_2_2[i].length; j++) {

                System.out.print(matrix_2_2[i][j]);

                if (j < matrix_2_2[i].length - 1) {
                    System.out.print(", ");
                }
            }

            System.out.print("]");

            if (i < matrix_2_2.length - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");


        // -------------------- matrix_3_1 --------------------

        System.out.print("[");

        for (int i = 0; i < matrix_3_1.length; i++) {

            System.out.print("[");

            for (int j = 0; j < matrix_3_1[i].length; j++) {

                System.out.print(matrix_3_1[i][j]);

                if (j < matrix_3_1[i].length - 1) {
                    System.out.print(", ");
                }
            }

            System.out.print("]");

            if (i < matrix_3_1.length - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");


        // =========================================================
        // PRINTING METHOD 3: ENHANCED FOR LOOP / FOR-EACH
        // =========================================================
        // Normally enhanced for loop prints:
        //
        // 1 2 3
        // 4 5 6
        // 7 8 9
        //
        // Here we manually add [ ] and commas
        // to produce the same format as deepToString().

        System.out.println("\n=========================================================");
        System.out.println("PRINTING METHOD 3: ENHANCED FOR LOOP / FOR-EACH");
        System.out.println("=========================================================");

        System.out.println("\nMatrix - Direct Initialization:");

        // -------------------- matrix --------------------

        System.out.print("[");

        int rowCount = 0;

        for (int[] row : matrix) {

            System.out.print("[");

            int valueCount = 0;

            for (int value : row) {

                System.out.print(value);

                if (valueCount < row.length - 1) {
                    System.out.print(", ");
                }

                valueCount++;
            }

            System.out.print("]");

            if (rowCount < matrix.length - 1) {
                System.out.print(", ");
            }

            rowCount++;
        }

        System.out.println("]");


        // -------------------- matrix_2_2 --------------------

        System.out.print("[");

        rowCount = 0;

        for (int[] row : matrix_2_2) {

            System.out.print("[");

            int valueCount = 0;

            for (int value : row) {

                System.out.print(value);

                if (valueCount < row.length - 1) {
                    System.out.print(", ");
                }

                valueCount++;
            }

            System.out.print("]");

            if (rowCount < matrix_2_2.length - 1) {
                System.out.print(", ");
            }

            rowCount++;
        }

        System.out.println("]");


        // -------------------- matrix_3_1 --------------------

        System.out.print("[");

        rowCount = 0;

        for (int[] row : matrix_3_1) {

            System.out.print("[");

            int valueCount = 0;

            for (int value : row) {

                System.out.print(value);

                if (valueCount < row.length - 1) {
                    System.out.print(", ");
                }

                valueCount++;
            }

            System.out.print("]");

            if (rowCount < matrix_3_1.length - 1) {
                System.out.print(", ");
            }

            rowCount++;
        }

        System.out.println("]");


        // =========================================================
        // PART 2
        // MATRIX FORMAT PRINTING
        // =========================================================
        //
        // Expected output:
        //
        // 1 2 3
        // 4 5 6
        // 7 8 9
        //
        // 1 2
        // 3 4
        //
        // 1
        // 3
        // 5
        //
        // This is the actual MATRIX STYLE.
        // =========================================================


        // =========================================================
        // MATRIX PRINTING METHOD 1: Arrays.toString()
        // =========================================================
        // deepToString() gives the complete 2D array in one line.
        //
        // Arrays.toString() works on a ONE-DIMENSIONAL array.
        //
        // Since every row of a 2D array is actually a 1D array,
        // we can print each row separately.
        //
        // Output:
        //
        // [1, 2, 3]
        // [4, 5, 6]
        // [7, 8, 9]

        System.out.println("\n=========================================================");
        System.out.println("MATRIX METHOD 1: Arrays.toString() FOR EACH ROW");
        System.out.println("=========================================================");

        System.out.println("\nMatrix - Direct Initialization:");

        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }

        System.out.println();

        for (int[] row : matrix_2_2) {
            System.out.println(Arrays.toString(row));
        }

        System.out.println();

        for (int[] row : matrix_3_1) {
            System.out.println(Arrays.toString(row));
        }


        // =========================================================
        // MATRIX PRINTING METHOD 2: NESTED FOR LOOP
        // =========================================================
        // i = row
        // j = column
        //
        // print() -> keeps values on same row
        // println() -> moves to next row

        System.out.println("\n=========================================================");
        System.out.println("MATRIX METHOD 2: NESTED FOR LOOP");
        System.out.println("=========================================================");

        System.out.println("\nMatrix - Direct Initialization:");

        // matrix

        for (int i = 0; i < matrix.length; i++) {

            for (int j = 0; j < matrix[i].length; j++) {

                System.out.print(matrix[i][j] + " ");
            }

            System.out.println();
        }

        System.out.println();

        // matrix_2_2

        for (int i = 0; i < matrix_2_2.length; i++) {

            for (int j = 0; j < matrix_2_2[i].length; j++) {

                System.out.print(matrix_2_2[i][j] + " ");
            }

            System.out.println();
        }

        System.out.println();

        // matrix_3_1

        for (int i = 0; i < matrix_3_1.length; i++) {

            for (int j = 0; j < matrix_3_1[i].length; j++) {

                System.out.print(matrix_3_1[i][j] + " ");
            }

            System.out.println();
        }


        // =========================================================
        // MATRIX PRINTING METHOD 3: ENHANCED FOR LOOP / FOR-EACH
        // =========================================================
        // row = one complete row
        // value = one individual element
        //
        // print() -> same line
        // println() -> next row

        System.out.println("\n=========================================================");
        System.out.println("MATRIX METHOD 3: ENHANCED FOR LOOP / FOR-EACH");
        System.out.println("=========================================================");

        System.out.println("\nMatrix - Direct Initialization:");

        // matrix

        for (int[] row : matrix) {

            for (int value : row) {

                System.out.print(value + " ");
            }

            System.out.println();
        }

        System.out.println();

        // matrix_2_2

        for (int[] row : matrix_2_2) {

            for (int value : row) {

                System.out.print(value + " ");
            }

            System.out.println();
        }

        System.out.println();

        // matrix_3_1

        for (int[] row : matrix_3_1) {

            for (int value : row) {

                System.out.print(value + " ");
            }

            System.out.println();
        }
    }
}
