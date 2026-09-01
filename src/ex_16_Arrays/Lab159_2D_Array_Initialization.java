package ex_16_Arrays;

import java.util.Arrays;

public class Lab159_2D_Array_Initialization {
    public static void main(String[] args) {

        // =========================================================   // 2D int	      int[][] a = {{1,2}, {3,4}};
        // 1. DIRECT INITIALIZATION / ARRAY INITIALIZER               //  2D int	      int[][] a = new int[][]{{1,2}, {3,4}};
        // ========================================================= //   2D int	      int[][] a = new int[2][2];  After declare the size we have to initialize values
        // Most common and shortest way to create a 2D array.
        //                                                         //     2D String	      String[][] a = {{"A","B"}, {"C","D"}};
        // Think:
        // int[][] = array of rows                               //       2D String	      String[][] a = new String[][]{{"A","B"}, {"C","D"}};
        //
        // Each { } represents one row.                         //        2D String	      String[][] a = new String[2][2];  After declare the size we have to initialize values

        int[][] matrix1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("1. DIRECT INITIALIZATION");

        System.out.println(Arrays.deepToString(matrix1));


        // =========================================================
        // 2. USING new int[][] WITH VALUES
        // =========================================================
        // We explicitly use new int[][].
        // Values are provided immediately.

        int[][] matrix2 = new int[][] {
                {10, 20, 30},
                {40, 50, 60},
                {70, 80, 90}
        };

        System.out.println("\n2. USING new int[][] WITH VALUES");

        System.out.println(Arrays.deepToString(matrix2));


        // =========================================================
        // 3. CREATE 2D ARRAY WITH ROWS AND COLUMNS
        // =========================================================
        // new int[2][3]
        //
        // 2 → number of rows
        // 3 → number of columns
        //
        // Initially all values are 0.

        int[][] matrix3 = new int[2][3];

        matrix3[0][0] = 100;
        matrix3[0][1] = 200;
        matrix3[0][2] = 300;

        matrix3[1][0] = 400;
        matrix3[1][1] = 500;
        matrix3[1][2] = 600;

        System.out.println("\n3. CREATE 2D ARRAY WITH SIZE + ASSIGN VALUES");

        System.out.println(Arrays.deepToString(matrix3));


        // =========================================================
        // 4. CREATE 2D ARRAY + FILL USING NESTED FOR LOOP
        // =========================================================
        // First create an empty 3 x 3 array.
        // Then use nested loops to assign values.

        int[][] matrix4 = new int[3][3];

        int value = 1;

        for (int i = 0; i < matrix4.length; i++) {

            for (int j = 0; j < matrix4[i].length; j++) {

                matrix4[i][j] = value;

                value++;
            }
        }

        System.out.println("\n4. CREATE 2D ARRAY + FILL USING NESTED LOOP");

        System.out.println(Arrays.deepToString(matrix4));


        // =========================================================
        // 5. JAGGED ARRAY - DIRECT INITIALIZATION
        // =========================================================
        // A jagged array has rows with DIFFERENT lengths.
        //
        // Row 0 → 2 elements
        // Row 1 → 4 elements
        // Row 2 → 3 elements

        int[][] matrix5 = {
                {11, 22},
                {33, 44, 55, 66},
                {77, 88, 99}
        };

        System.out.println("\n5. JAGGED ARRAY - DIRECT INITIALIZATION");

        System.out.println(Arrays.deepToString(matrix5));


        // =========================================================
        // 6. JAGGED ARRAY - USING new int[][]
        // =========================================================
        // First create the rows.
        // Then each row can have a different size.

        int[][] matrix6 = new int[3][];

        matrix6[0] = new int[] {101, 102};

        matrix6[1] = new int[] {103, 104, 105};

        matrix6[2] = new int[] {106, 107, 108, 109};

        System.out.println("\n6. JAGGED ARRAY - USING new int[][]");

        System.out.println(Arrays.deepToString(matrix6));


        // =========================================================
        // 7. JAGGED ARRAY - CREATE ROWS WITH DIFFERENT SIZES
        // =========================================================
        // Here we first specify the number of rows.
        // Then manually specify the size of each row.

        int[][] matrix7 = new int[3][];

        matrix7[0] = new int[2];
        matrix7[1] = new int[3];
        matrix7[2] = new int[4];

        matrix7[0][0] = 201;
        matrix7[0][1] = 202;

        matrix7[1][0] = 203;
        matrix7[1][1] = 204;
        matrix7[1][2] = 205;

        matrix7[2][0] = 206;
        matrix7[2][1] = 207;
        matrix7[2][2] = 208;
        matrix7[2][3] = 209;

        System.out.println("\n7. JAGGED ARRAY - CREATE ROWS SEPARATELY");

        System.out.println(Arrays.deepToString(matrix7));


        // =========================================================
        // 8. STRING 2D ARRAY - DIRECT INITIALIZATION
        // =========================================================

        String[][] names1 = {
                {"Rahul", "Amit"},
                {"Priya", "Neha"}
        };

        System.out.println("\n8. STRING 2D ARRAY - DIRECT INITIALIZATION");

        System.out.println(Arrays.deepToString(names1));


        // =========================================================
        // 9. STRING 2D ARRAY - USING new String[][]
        // =========================================================

        String[][] names2 = new String[][] {
                {"Java", "Python"},
                {"C++", "JavaScript"}
        };

        System.out.println("\n9. STRING 2D ARRAY - USING new String[][]");

        System.out.println(Arrays.deepToString(names2));


        // =========================================================
        // 10. STRING 2D ARRAY - CREATE WITH SIZE
        // =========================================================
        // Default value of String elements is null.

        String[][] names3 = new String[2][2];

        names3[0][0] = "Apple";
        names3[0][1] = "Mango";

        names3[1][0] = "Orange";
        names3[1][1] = "Banana";

        System.out.println("\n10. STRING 2D ARRAY - CREATE WITH SIZE");

        System.out.println(Arrays.deepToString(names3));


        // =========================================================
        // PRINTING METHOD 1: Arrays.deepToString()
        // =========================================================
        // Best for quickly printing a complete 2D array.

        System.out.println("\n=========================================================");
        System.out.println("PRINTING METHOD 1: Arrays.deepToString()");
        System.out.println("=========================================================");

        System.out.println(Arrays.deepToString(matrix1));


        // =========================================================
        // PRINTING METHOD 2: NESTED FOR LOOP
        // =========================================================
        // Outer loop → rows
        // Inner loop → columns

        System.out.println("\n=========================================================");
        System.out.println("PRINTING METHOD 2: NESTED FOR LOOP");
        System.out.println("=========================================================");

        for (int i = 0; i < matrix1.length; i++) {

            for (int j = 0; j < matrix1[i].length; j++) {

                System.out.print(matrix1[i][j] + " ");
            }

            System.out.println();
        }


        // =========================================================
        // PRINTING METHOD 3: ENHANCED FOR LOOP / FOR-EACH
        // =========================================================
        // First loop → gets each row
        // Second loop → gets each value inside that row

        System.out.println("\n=========================================================");
        System.out.println("PRINTING METHOD 3: ENHANCED FOR LOOP / FOR-EACH");
        System.out.println("=========================================================");

        for (int[] row : matrix1) {

            for (int element : row) {

                System.out.print(element + " ");
            }

            System.out.println();
        }
    }
}


