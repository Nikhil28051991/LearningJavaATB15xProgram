package ex_16_Arrays;

import java.util.Arrays;

public class Lab151_1D_ArrayInitialization {
    public static void main(String[] args) {

        // =========================================================      //  1D int	    int[] a = {1, 2, 3};
        // 1. DIRECT INITIALIZATION / ARRAY INITIALIZER                  //   1D int	    int[] a = new int[]{1, 2, 3};
        // =========================================================    //    1D int	    int[] a = new int[3]; After declare the size we have to initialize values
        // Most common and shortest way.
        // Values are directly provided inside { }.                   //      1D String	    String[] a = {"A", "B", "C"};
                                                                     //       1D String	    String[] a = new String[]{"A", "B", "C"};
        int[] array1 = {10, 20, 30, 40, 50};                        //        1D String	    String1[] a = new String[3]; After declare the size we have to initialize values

        System.out.println("1. DIRECT INITIALIZATION");
        System.out.println(Arrays.toString(array1));


        // =========================================================
        // 2. USING new int[] WITH VALUES
        // =========================================================
        // We explicitly use new int[].
        // Values are provided at the time of creation.

        int[] array2 = new int[] {60, 70, 80, 90, 100};

        System.out.println("\n2. USING new int[] WITH VALUES");
        System.out.println(Arrays.toString(array2));


        // =========================================================
        // 3. CREATE ARRAY WITH SIZE
        // =========================================================
        // Here we only specify the size.
        //
        // Java automatically gives default values.
        // For int, the default value is 0.

        int[] array3 = new int[5];

        array3[0] = 110;
        array3[1] = 120;
        array3[2] = 130;
        array3[3] = 140;
        array3[4] = 150;

        System.out.println("\n3. CREATE ARRAY WITH SIZE + ASSIGN VALUES");
        System.out.println(Arrays.toString(array3));


        // =========================================================
        // 4. CREATE ARRAY WITH SIZE + FILL USING LOOP
        // =========================================================
        // First create the array.
        // Then use a loop to put values into it.

        int[] array4 = new int[5];

        for (int i = 0; i < array4.length; i++) {

            array4[i] = (i + 1) * 10;
        }

        System.out.println("\n4. CREATE ARRAY + FILL USING LOOP");
        System.out.println(Arrays.toString(array4));


        // =========================================================
        // 5. CREATE ARRAY + Arrays.fill()
        // =========================================================
        // Arrays.fill() puts the SAME value
        // into every element.

        int[] array5 = new int[5];

        Arrays.fill(array5, 999);

        System.out.println("\n5. CREATE ARRAY + Arrays.fill()");
        System.out.println(Arrays.toString(array5));


        // =========================================================
        // 6. STRING ARRAY - DIRECT INITIALIZATION
        // =========================================================
        // Same concept works with String arrays.

        String[] names1 = {
                "Rahul",
                "Amit",
                "Priya",
                "Neha"
        };

        System.out.println("\n6. STRING ARRAY - DIRECT INITIALIZATION");
        System.out.println(Arrays.toString(names1));


        // =========================================================
        // 7. STRING ARRAY - USING new String[]
        // =========================================================

        String[] names2 = new String[] {
                "Java",
                "Python",
                "C++",
                "JavaScript"
        };

        System.out.println("\n7. STRING ARRAY - USING new String[]");
        System.out.println(Arrays.toString(names2));


        // =========================================================
        // 8. STRING ARRAY - CREATE WITH SIZE
        // =========================================================
        // Default value of String array elements is null.

        String[] names3 = new String[4];

        names3[0] = "Apple";
        names3[1] = "Mango";
        names3[2] = "Orange";
        names3[3] = "Banana";

        System.out.println("\n8. STRING ARRAY - CREATE WITH SIZE + ASSIGN");
        System.out.println(Arrays.toString(names3));


        // =========================================================
        // 9. STRING ARRAY - CREATE + FILL USING LOOP
        // =========================================================

        String[] names4 = new String[4];

        names4[0] = "Red";
        names4[1] = "Green";
        names4[2] = "Blue";
        names4[3] = "Yellow";

        System.out.println("\n9. STRING ARRAY - CREATE + FILL");
        System.out.println(Arrays.toString(names4));


        // =========================================================
        // 10. PRINTING USING NORMAL FOR LOOP
        // =========================================================
        // We can access every element using its index.

        System.out.println("\n10. PRINTING USING NORMAL FOR LOOP");

        for (int i = 0; i < array1.length; i++) {

            System.out.print(array1[i] + " ");
        }

        System.out.println();


        // =========================================================
        // 11. PRINTING USING ENHANCED FOR LOOP / FOR-EACH
        // =========================================================
        // No index is required.
        // The variable 'value' directly receives each element.

        System.out.println("\n11. PRINTING USING ENHANCED FOR LOOP");

        for (int value : array2) {

            System.out.print(value + " ");
        }

        System.out.println();
    }
}

