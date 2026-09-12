package ex_16_Arrays;

public class ArrayDeclaration {

    public static void main(String[] args) {

        // ============================================================
        // 1. int Array
        // ============================================================

        int[] arr1;                       // arr1 → Stack. No Array Object created yet.
        arr1 = new int[3];                // arr1 → Stack | Array Object → Heap Area / Object Area

        int[] arr2 = {10, 20, 30};        // arr2 → Stack | Array Object → Heap Area / Object Area


        // ============================================================
        // 2. float Array
        // ============================================================

        float[] arr3;                     // arr3 → Stack. No Array Object created yet.
        arr3 = new float[3];              // arr3 → Stack | Array Object → Heap Area / Object Area

        float[] arr4 = {10.5f, 20.5f, 30.5f};
        // arr4 → Stack | Array Object → Heap Area / Object Area


        // ============================================================
        // 3. double Array
        // ============================================================

        double[] arr5;                    // arr5 → Stack. No Array Object created yet.
        arr5 = new double[3];             // arr5 → Stack | Array Object → Heap Area / Object Area

        double[] arr6 = {10.5, 20.5, 30.5};
        // arr6 → Stack | Array Object → Heap Area / Object Area


        // ============================================================
        // 4. char Array
        // ============================================================

        char[] arr7;                      // arr7 → Stack. No Array Object created yet.
        arr7 = new char[3];               // arr7 → Stack | Array Object → Heap Area / Object Area

        char[] arr8 = {'A', 'B', 'C'};    // arr8 → Stack | Array Object → Heap Area / Object Area
        // 'A', 'B', 'C' → char values, NOT String literals.


        // ============================================================
        // 5. boolean Array
        // ============================================================

        boolean[] arr9;                   // arr9 → Stack. No Array Object created yet.
        arr9 = new boolean[3];            // arr9 → Stack | Array Object → Heap Area / Object Area

        boolean[] arr10 = {true, false, true};
        // arr10 → Stack | Array Object → Heap Area / Object Area


        // ============================================================
        // 6. String Array
        // ============================================================

        String[] arr11;                   // arr11 → Stack. No Array Object created yet.
        arr11 = new String[3];            // arr11 → Stack | Array Object → Heap Area / Object Area
        // Array elements initially contain null.

        String[] arr12 = {"Nikhil", "Amit", "Rahul"};
        // arr12 → Stack | String Array Object → Heap Area / Object Area
        // "Nikhil" → String Constant Pool (SCP)
        // "Amit"   → String Constant Pool (SCP)
        // "Rahul"  → String Constant Pool (SCP)


        // ============================================================
        // 7. Declare multiple Arrays separately
        // ============================================================

        int[] arr13;                      // arr13 → Stack. No Array Object created yet.
        float[] arr14;                    // arr14 → Stack. No Array Object created yet.
        double[] arr15;                   // arr15 → Stack. No Array Object created yet.
        char[] arr16;                     // arr16 → Stack. No Array Object created yet.
        boolean[] arr17;                  // arr17 → Stack. No Array Object created yet.
        String[] arr18;                   // arr18 → Stack. No Array Object created yet.

        arr13 = new int[5];               // arr13 → Stack | Array Object → Heap Area / Object Area
        arr14 = new float[5];             // arr14 → Stack | Array Object → Heap Area / Object Area
        arr15 = new double[5];            // arr15 → Stack | Array Object → Heap Area / Object Area
        arr16 = new char[5];              // arr16 → Stack | Array Object → Heap Area / Object Area
        arr17 = new boolean[5];           // arr17 → Stack | Array Object → Heap Area / Object Area
        arr18 = new String[5];            // arr18 → Stack | Array Object → Heap Area / Object Area


        // ============================================================
        // 8. Same values but TWO different Arrays
        // ============================================================

        int[] arr19 = {10, 20, 30};       // arr19 → Stack | Array Object → Heap Area / Object Area
        int[] arr20 = {10, 20, 30};       // arr20 → Stack | DIFFERENT Array Object → Heap Area / Object Area

        System.out.println(arr19 == arr20); // Output: false
        // == compares Array references.


        // ============================================================
        // 9. Two references pointing to SAME Array
        // ============================================================

        int[] arr21 = {100, 200, 300};    // arr21 → Stack | Array Object → Heap Area / Object Area
        int[] arr22 = arr21;              // arr22 → Stack | Points to SAME Array Object in Heap Area / Object Area

        System.out.println(arr21 == arr22); // Output: true


        // ============================================================
        // 10. Default values
        // ============================================================

        int[] numbers = new int[3];       // numbers → Stack | Array Object → Heap Area / Object Area | Default = 0

        float[] prices = new float[3];    // prices → Stack | Array Object → Heap Area / Object Area | Default = 0.0f

        double[] values = new double[3];  // values → Stack | Array Object → Heap Area / Object Area | Default = 0.0

        char[] letters = new char[3];     // letters → Stack | Array Object → Heap Area / Object Area | Default = '\u0000'

        boolean[] flags = new boolean[3]; // flags → Stack | Array Object → Heap Area / Object Area | Default = false

        String[] names = new String[3];   // names → Stack | Array Object → Heap Area / Object Area | Default = null


        // ============================================================
        // 11. String Object vs String Literal
        // ============================================================

        String s1 = "Nikhil";              // s1 → Stack | "Nikhil" → String Constant Pool (SCP)

        String s2 = new String("Nikhil");  // s2 → Stack | NEW String Object → Heap Area / Object Area
        // "Nikhil" literal → String Constant Pool (SCP)


        // ============================================================
        // 12. String Array + Heap Area / Object Area + SCP
        // ============================================================

        String[] names2 = {"Nikhil", "Amit"};
        // names2 → Stack | String Array Object → Heap Area / Object Area
        // "Nikhil" → String Constant Pool (SCP)
        // "Amit"   → String Constant Pool (SCP)


        // ============================================================
        // 13. Array Length
        // ============================================================

        int[] marks = {90, 80, 70, 60};   // marks → Stack | Array Object → Heap Area / Object Area

        System.out.println(marks.length); // Output: 4
        // Array uses length, NOT length()


        // ============================================================
        // QUICK INTERVIEW REMEMBER
        // ============================================================

        // Local Array Reference → Stack

        // int[] / float[] / double[] / char[] / boolean[] / String[]
        // Actual Array Object → Heap Area / Object Area

        // "Nikhil" / "Java" / "Hello"
        // String Literal → String Constant Pool (SCP)

        // new String("Nikhil")
        // NEW String Object → Heap Area / Object Area
        // "Nikhil" literal → String Constant Pool (SCP)

        // IMPORTANT:
        // String Constant Pool (SCP) is a special area within the Heap Area
        // in modern Java.

        // Array → length
        // String → length()
        // Collection → size()

    }
}