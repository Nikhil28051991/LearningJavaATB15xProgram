package ex_16_Arrays;

import java.util.Arrays;

public class Lab161_2Dp1 {
    public static void main(String[] args) {

        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] matrix_2_2 = {{1, 2}, {3, 4}};
        int[][] matrix_3_1 = {{1}, {3}, {5}};

        System.out.println("\n=========================================================");
        System.out.println("PRINTING METHOD 1: Arrays.deepToString()");
        System.out.println("=========================================================");


        System.out.println("\nMatrix  - Direct Initialization:");
        System.out.println(Arrays.deepToString(matrix));
        System.out.println(Arrays.deepToString(matrix_2_2));
        System.out.println(Arrays.deepToString(matrix_3_1));

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

                // Add comma between elements
                // but NOT after the last element.
                if (j < matrix[i].length - 1) {
                    System.out.print(", ");
                }
            }

            System.out.print("]");

            // Add comma between rows
            // but NOT after the last row.
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
        // Enhanced for loop is also called for-each loop.
        //
        // int[] row -> gets one complete row
        // int value -> gets one element from that row
        //
        // Normally, this would print:
        //
        // 1 2 3
        // 4 5 6
        // 7 8 9
        //
        // But just like Method 2, we manually add [ ], commas
        // to make it look like Arrays.deepToString().
        //
        // IMPORTANT:
        // Enhanced for-each is mainly useful when you want to
        // READ/access elements and don't need the index i or j.

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

                // Add comma between elements
                // but NOT after the last element.
                if (valueCount < row.length - 1) {
                    System.out.print(", ");
                }

                valueCount++;
            }

            System.out.print("]");

            // Add comma between rows
            // but NOT after the last row.
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
    }

}
