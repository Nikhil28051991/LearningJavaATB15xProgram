package ex_16_Arrays;

import java.util.Arrays;

public class Lab160_2DRepresentation {
    public static void main(String[] args) {

        // =========================================================
        // 1. DIRECT INITIALIZATION / ARRAY INITIALIZER
        // =========================================================
        // Most common and shortest way.
        // You directly provide the values.

        int[][] matrix1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        // PRINTING METHOD 1: Arrays.deepToString()
        // Best for quickly printing the complete 2D array.

        System.out.println("1. Direct Initialization");
        System.out.println("Using Arrays.deepToString():");
        System.out.println(Arrays.deepToString(matrix1));

        // PRINTING METHOD 2: NESTED FOR LOOP
        // Outer loop -> rows
        // Inner loop -> columns

        System.out.println("Using nested for loop:");

        for (int i = 0; i < matrix1.length; i++) {

            for (int j = 0; j < matrix1[i].length; j++) {

                System.out.print(matrix1[i][j] + " ");
            }

            System.out.println();
        }

        // PRINTING METHOD 3: ENHANCED FOR LOOP / FOR-EACH
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
        // Same result as above, but explicitly uses new int[][].

        int[][] matrix2 = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        // PRINTING METHOD 1: Arrays.deepToString()

        System.out.println("\n2. Using new int[][]");
        System.out.println("Using Arrays.deepToString():");
        System.out.println(Arrays.deepToString(matrix2));

        // PRINTING 2: NESTED FOR LOOP

        System.out.println("Using nested for loop:");

        for (int i = 0; i < matrix2.length; i++) {

            for (int j = 0; j < matrix2[i].length; j++) {

                System.out.print(matrix2[i][j] + " ");
            }

            System.out.println();
        }

        // PRINTING 3: ENHANCED FOR LOOP

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
        // Here we create a 3 x 3 array.
        // Java automatically fills it with 0.

        int[][] matrix3 = new int[3][3];

        // Then assign values individually.
        matrix3[0][0] = 1;
        matrix3[0][1] = 2;
        matrix3[0][2] = 3;

        matrix3[1][0] = 4;
        matrix3[1][1] = 5;
        matrix3[1][2] = 6;

        matrix3[2][0] = 7;
        matrix3[2][1] = 8;
        matrix3[2][2] = 9;

        // PRINTING 1: Arrays.deepToString()

        System.out.println("\n3. Create Array With Size");
        System.out.println("Using Arrays.deepToString():");
        System.out.println(Arrays.deepToString(matrix3));

        // PRINTING 2: NESTED FOR LOOP

        System.out.println("Using nested for loop:");

        for (int i = 0; i < matrix3.length; i++) {

            for (int j = 0; j < matrix3[i].length; j++) {

                System.out.print(matrix3[i][j] + " ");
            }

            System.out.println();
        }


        // PRINTING 3: ENHANCED FOR LOOP

        System.out.println("Using enhanced for loop:");

        for (int[] row : matrix3) {

            for (int value : row) {

                System.out.print(value + " ");
            }

            System.out.println();
        }


        // =========================================================
        // 4. CREATE ARRAY WITH SIZE + INITIALIZE ROWS
        // =========================================================
        // First create the 2D array.
        // Then create each row separately.

        int[][] matrix4 = new int[3][];

        matrix4[0] = new int[] {1, 2, 3};
        matrix4[1] = new int[] {4, 5, 6};
        matrix4[2] = new int[] {7, 8, 9};

        // PRINTING 1: Arrays.deepToString()

        System.out.println("\n4. Assign Values Individually");
        System.out.println("Using Arrays.deepToString():");
        System.out.println(Arrays.deepToString(matrix4));

        // PRINTING 2: NESTED FOR LOOP

        System.out.println("Using nested for loop:");

        for (int i = 0; i < matrix4.length; i++) {

            for (int j = 0; j < matrix4[i].length; j++) {

                System.out.print(matrix4[i][j] + " ");
            }

            System.out.println();
        }


        // PRINTING 3: ENHANCED FOR LOOP

        System.out.println("Using enhanced for loop:");

        for (int[] row : matrix4) {

            for (int value : row) {

                System.out.print(value + " ");
            }

            System.out.println();
        }


        // =========================================================
        // 5. USING LOOPS
        // =========================================================
        // Create an empty 3 x 3 array,
        // then fill it using nested loops.

        int[][] matrix5 = new int[3][3];

        int value = 1;

        for (int i = 0; i < matrix5.length; i++) {

            for (int j = 0; j < matrix5[i].length; j++) {

                matrix5[i][j] = value;
                value++;
            }
        }

        // PRINTING METHOD 1: Arrays.deepToString()
        // Best for quickly printing the complete 2D array.

        System.out.println("\n5. Using Loops");
        System.out.println("Using Arrays.deepToString():");
        System.out.println(Arrays.deepToString(matrix5));


        // PRINTING METHOD 2: NESTED FOR LOOP
        // Outer loop -> rows
        // Inner loop -> columns

        System.out.println("Using nested for loop:");

        for (int i = 0; i < matrix5.length; i++) {

            for (int j = 0; j < matrix5[i].length; j++) {

                System.out.print(matrix5[i][j] + " ");
            }

            System.out.println();
        }


        // PRINTING METHOD 3: ENHANCED FOR LOOP / FOR-EACH
        // First for loop gets each row.
        // Second for loop gets each value from that row.

        System.out.println("Using enhanced for loop:");

        for (int[] row : matrix5) {

            for (int value1 : row) {

                System.out.print(value1 + " ");
            }

            System.out.println();
        }


        // =========================================================
        // 6. JAGGED ARRAY
        // =========================================================
        // Each row can have a DIFFERENT number of elements.
        // This is possible because Java's 2D arrays are arrays of arrays.

        int[][] matrix6 = {
                {1, 2, 3},
                {4, 5},
                {6, 7, 8, 9}
        };

        // PRINTING METHOD 1: Arrays.deepToString()
        // Best for quickly printing the complete 2D jagged array.

        System.out.println("\n6. Jagged Array");
        System.out.println("Using Arrays.deepToString():");
        System.out.println(Arrays.deepToString(matrix6));


        // PRINTING METHOD 2: NESTED FOR LOOP
        // Outer loop -> rows
        // Inner loop -> columns
        // matrix6[i].length gets the length of the current row.
        // This is important because every row has a different length.

        System.out.println("Using nested for loop:");

        for (int i = 0; i < matrix6.length; i++) {

            for (int j = 0; j < matrix6[i].length; j++) {

                System.out.print(matrix6[i][j] + " ");
            }

            System.out.println();
        }


        // PRINTING METHOD 3: ENHANCED FOR LOOP / FOR-EACH
        // First for loop gets each row.
        // Second for loop gets each value from that row.
        // This works very well with jagged arrays.

        System.out.println("Using enhanced for loop:");

        for (int[] row : matrix6) {

            for (int value1 : row) {

                System.out.print(value1 + " ");
            }

            System.out.println();
        }



        // =========================================================
        // 7. CREATE JAGGED ARRAY USING new
        // =========================================================

        int[][] matrix7 = new int[3][];

        matrix7[0] = new int[2];  // 2 elements
        matrix7[1] = new int[4];  // 4 elements
        matrix7[2] = new int[1];  // 1 element

        matrix7[0][0] = 10;
        matrix7[0][1] = 20;

        matrix7[1][0] = 30;
        matrix7[1][1] = 40;
        matrix7[1][2] = 50;
        matrix7[1][3] = 60;

        matrix7[2][0] = 70;

        // PRINTING METHOD 1: Arrays.deepToString()
        // Best for quickly printing the complete 2D jagged array.

        System.out.println("\n7. Create Jagged Array Using new");
        System.out.println("Using Arrays.deepToString():");
        System.out.println(Arrays.deepToString(matrix7));


        // PRINTING METHOD 2: NESTED FOR LOOP
        // Outer loop -> rows
        // Inner loop -> columns
        // matrix7[i].length gives the size of the current row.

        System.out.println("Using nested for loop:");

        for (int i = 0; i < matrix7.length; i++) {

            for (int j = 0; j < matrix7[i].length; j++) {

                System.out.print(matrix7[i][j] + " ");
            }

            System.out.println();
        }


        // PRINTING METHOD 3: ENHANCED FOR LOOP / FOR-EACH
        // First for loop gets each row.
        // Second for loop gets each value from that row.

        System.out.println("Using enhanced for loop:");

        for (int[] row : matrix7) {

            for (int value1 : row) {

                System.out.print(value1 + " ");
            }

            System.out.println();
        }


        // =========================================================
        // PRINTING AN ARRAY
        // =========================================================
        // Nested loops are commonly used to print a 2D array.

        System.out.println("Matrix 1:");

        for (int i = 0; i < matrix1.length; i++) {

            for (int j = 0; j < matrix1[i].length; j++) {

                System.out.print(matrix1[i][j] + " ");
            }

            System.out.println();
        }
    }
}


