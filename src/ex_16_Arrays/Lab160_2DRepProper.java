package ex_16_Arrays;

import java.util.Arrays;

public class Lab160_2DRepProper {
    public static void main(String[] args) {

        // =========================================================
        // 1. DIRECT INITIALIZATION
        // =========================================================
        // Also called: Array Initializer
        // Values are directly provided while declaring the array.

        int[][] matrix1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };


        // ---------------------------------------------------------
        // PRINTING METHOD 1: Arrays.deepToString()
        // ---------------------------------------------------------
        // Best for quickly printing the complete 2D array.

        System.out.println("1. Direct Initialization");
        System.out.println("Using Arrays.deepToString():");
        System.out.println(Arrays.deepToString(matrix1));


        // ---------------------------------------------------------
        // PRINTING METHOD 2: NESTED FOR LOOP
        // ---------------------------------------------------------
        // Outer loop -> rows
        // Inner loop -> columns

        System.out.println("Using nested for loop:");

        for (int i = 0; i < matrix1.length; i++) {

            for (int j = 0; j < matrix1[i].length; j++) {

                System.out.print(matrix1[i][j] + " ");
            }

            System.out.println();
        }


        // ---------------------------------------------------------
        // PRINTING METHOD 3: ENHANCED FOR LOOP / FOR-EACH
        // ---------------------------------------------------------
        // First for loop gets each row.
        // Second for loop gets each value from that row.

        System.out.println("Using enhanced for loop:");

        for (int[] row : matrix1) {

            for (int value : row) {

                System.out.print(value + " ");
            }

            System.out.println();
        }


        // =========================================================
        // 2. USING new int[][] WITH VALUES
        // =========================================================
        // We explicitly use new int[][] while providing values.

        int[][] matrix2 = new int[][] {
                {11, 22},
                {33, 44},
                {55, 66}
        };


        // ---------------------------------------------------------
        // PRINTING 1: Arrays.deepToString()
        // ---------------------------------------------------------

        System.out.println("\n2. Using new int[][]");
        System.out.println("Using Arrays.deepToString():");
        System.out.println(Arrays.deepToString(matrix2));


        // ---------------------------------------------------------
        // PRINTING 2: NESTED FOR LOOP
        // ---------------------------------------------------------

        System.out.println("Using nested for loop:");

        for (int i = 0; i < matrix2.length; i++) {

            for (int j = 0; j < matrix2[i].length; j++) {

                System.out.print(matrix2[i][j] + " ");
            }

            System.out.println();
        }


        // ---------------------------------------------------------
        // PRINTING 3: ENHANCED FOR LOOP
        // ---------------------------------------------------------

        System.out.println("Using enhanced for loop:");

        for (int[] row : matrix2) {

            for (int value : row) {

                System.out.print(value + " ");
            }

            System.out.println();
        }


        // =========================================================
        // 3. CREATE ARRAY WITH SIZE
        // =========================================================
        // Here we specify the number of rows and columns.
        // Java initially fills the array with 0.

        int[][] matrix3 = new int[2][3];


        // Assign some values so we can see them while printing.

        matrix3[0][0] = 101;
        matrix3[0][1] = 102;
        matrix3[0][2] = 103;

        matrix3[1][0] = 104;
        matrix3[1][1] = 105;
        matrix3[1][2] = 106;


        // ---------------------------------------------------------
        // PRINTING 1: Arrays.deepToString()
        // ---------------------------------------------------------

        System.out.println("\n3. Create Array With Size");
        System.out.println("Using Arrays.deepToString():");
        System.out.println(Arrays.deepToString(matrix3));


        // ---------------------------------------------------------
        // PRINTING 2: NESTED FOR LOOP
        // ---------------------------------------------------------

        System.out.println("Using nested for loop:");

        for (int i = 0; i < matrix3.length; i++) {

            for (int j = 0; j < matrix3[i].length; j++) {

                System.out.print(matrix3[i][j] + " ");
            }

            System.out.println();
        }


        // ---------------------------------------------------------
        // PRINTING 3: ENHANCED FOR LOOP
        // ---------------------------------------------------------

        System.out.println("Using enhanced for loop:");

        for (int[] row : matrix3) {

            for (int value : row) {

                System.out.print(value + " ");
            }

            System.out.println();
        }


        // =========================================================
        // 4. CREATE ARRAY + ASSIGN VALUES INDIVIDUALLY
        // =========================================================
        // First create the array.
        // Then assign values using indexes.

        int[][] matrix4 = new int[2][2];

        matrix4[0][0] = 201;
        matrix4[0][1] = 202;

        matrix4[1][0] = 203;
        matrix4[1][1] = 204;


        // ---------------------------------------------------------
        // PRINTING 1: Arrays.deepToString()
        // ---------------------------------------------------------

        System.out.println("\n4. Assign Values Individually");
        System.out.println("Using Arrays.deepToString():");
        System.out.println(Arrays.deepToString(matrix4));


        // ---------------------------------------------------------
        // PRINTING 2: NESTED FOR LOOP
        // ---------------------------------------------------------

        System.out.println("Using nested for loop:");

        for (int i = 0; i < matrix4.length; i++) {

            for (int j = 0; j < matrix4[i].length; j++) {

                System.out.print(matrix4[i][j] + " ");
            }

            System.out.println();
        }


        // ---------------------------------------------------------
        // PRINTING 3: ENHANCED FOR LOOP
        // ---------------------------------------------------------

        System.out.println("Using enhanced for loop:");

        for (int[] row : matrix4) {

            for (int value : row) {

                System.out.print(value + " ");
            }

            System.out.println();
        }


        // =========================================================
        // 5. INITIALIZE EACH ROW SEPARATELY
        // =========================================================
        // First create the outer array.
        // Then create each row separately.

        int[][] matrix5 = new int[3][];

        matrix5[0] = new int[] {301, 302};
        matrix5[1] = new int[] {303, 304, 305};
        matrix5[2] = new int[] {306, 307, 308, 309};


        // ---------------------------------------------------------
        // PRINTING 1: Arrays.deepToString()
        // ---------------------------------------------------------

        System.out.println("\n5. Initialize Each Row Separately");
        System.out.println("Using Arrays.deepToString():");
        System.out.println(Arrays.deepToString(matrix5));


        // ---------------------------------------------------------
        // PRINTING 2: NESTED FOR LOOP
        // ---------------------------------------------------------
        // Notice that matrix5 is a jagged array.
        // Therefore matrix5[i].length is important.

        System.out.println("Using nested for loop:");

        for (int i = 0; i < matrix5.length; i++) {

            for (int j = 0; j < matrix5[i].length; j++) {

                System.out.print(matrix5[i][j] + " ");
            }

            System.out.println();
        }


        // ---------------------------------------------------------
        // PRINTING 3: ENHANCED FOR LOOP
        // ---------------------------------------------------------

        System.out.println("Using enhanced for loop:");

        for (int[] row : matrix5) {

            for (int value : row) {

                System.out.print(value + " ");
            }

            System.out.println();
        }


        // =========================================================
        // 6. INITIALIZATION USING NESTED LOOPS
        // =========================================================
        // First create an empty 3 x 3 array.
        // Then use nested loops to put values into it.

        int[][] matrix6 = new int[3][3];

        int number = 401;

        for (int i = 0; i < matrix6.length; i++) {

            for (int j = 0; j < matrix6[i].length; j++) {

                matrix6[i][j] = number;
                number += 10;
            }
        }


        // ---------------------------------------------------------
        // PRINTING 1: Arrays.deepToString()
        // ---------------------------------------------------------

        System.out.println("\n6. Initialization Using Nested Loops");
        System.out.println("Using Arrays.deepToString():");
        System.out.println(Arrays.deepToString(matrix6));


        // ---------------------------------------------------------
        // PRINTING 2: NESTED FOR LOOP
        // ---------------------------------------------------------

        System.out.println("Using nested for loop:");

        for (int i = 0; i < matrix6.length; i++) {

            for (int j = 0; j < matrix6[i].length; j++) {

                System.out.print(matrix6[i][j] + " ");
            }

            System.out.println();
        }


        // ---------------------------------------------------------
        // PRINTING 3: ENHANCED FOR LOOP
        // ---------------------------------------------------------

        System.out.println("Using enhanced for loop:");

        for (int[] row : matrix6) {

            for (int value : row) {

                System.out.print(value + " ");
            }

            System.out.println();
        }


        // =========================================================
        // 7. JAGGED ARRAY - DIRECT INITIALIZATION
        // =========================================================
        // Each row has a DIFFERENT number of elements.

        int[][] matrix7 = {
                {501, 502},
                {503, 504, 505},
                {506},
                {507, 508, 509, 510}
        };


        // ---------------------------------------------------------
        // PRINTING 1: Arrays.deepToString()
        // ---------------------------------------------------------

        System.out.println("\n7. Jagged Array - Direct Initialization");
        System.out.println("Using Arrays.deepToString():");
        System.out.println(Arrays.deepToString(matrix7));


        // ---------------------------------------------------------
        // PRINTING 2: NESTED FOR LOOP
        // ---------------------------------------------------------

        System.out.println("Using nested for loop:");

        for (int i = 0; i < matrix7.length; i++) {

            for (int j = 0; j < matrix7[i].length; j++) {

                System.out.print(matrix7[i][j] + " ");
            }

            System.out.println();
        }


        // ---------------------------------------------------------
        // PRINTING 3: ENHANCED FOR LOOP
        // ---------------------------------------------------------

        System.out.println("Using enhanced for loop:");

        for (int[] row : matrix7) {

            for (int value : row) {

                System.out.print(value + " ");
            }

            System.out.println();
        }


        // =========================================================
        // 8. JAGGED ARRAY - USING new
        // =========================================================
        // We decide the size of every row separately.

        int[][] matrix8 = new int[3][];

        matrix8[0] = new int[3];
        matrix8[1] = new int[2];
        matrix8[2] = new int[4];


        // Assign values.

        matrix8[0][0] = 601;
        matrix8[0][1] = 602;
        matrix8[0][2] = 603;

        matrix8[1][0] = 604;
        matrix8[1][1] = 605;

        matrix8[2][0] = 606;
        matrix8[2][1] = 607;
        matrix8[2][2] = 608;
        matrix8[2][3] = 609;


        // ---------------------------------------------------------
        // PRINTING 1: Arrays.deepToString()
        // ---------------------------------------------------------

        System.out.println("\n8. Jagged Array - Using new");
        System.out.println("Using Arrays.deepToString():");
        System.out.println(Arrays.deepToString(matrix8));


        // ---------------------------------------------------------
        // PRINTING 2: NESTED FOR LOOP
        // ---------------------------------------------------------

        System.out.println("Using nested for loop:");

        for (int i = 0; i < matrix8.length; i++) {

            for (int j = 0; j < matrix8[i].length; j++) {

                System.out.print(matrix8[i][j] + " ");
            }

            System.out.println();
        }


        // ---------------------------------------------------------
        // PRINTING 3: ENHANCED FOR LOOP
        // ---------------------------------------------------------

        System.out.println("Using enhanced for loop:");

        for (int[] row : matrix8) {

            for (int value : row) {

                System.out.print(value + " ");
            }

            System.out.println();
        }
    }
}
