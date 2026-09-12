package ex_14_Strings;

public class StringExamRecall_2 {

        public static void main(String[] args) {

            // ============================================================
            // 1) charAt() → Get character using index
            // ============================================================

            String s = "Java";

            // J  a  v  a
            // 0  1  2  3

            char c = s.charAt(2);                  // Index 2 → v
            System.out.println(c);                 // Output: v


            // ============================================================
            // 2) compareTo() → Compare two Strings
            // ============================================================

            int result = "ABC".compareTo("abc");   // Output: -32
            int result1 = "abc".compareTo("abc");  // Output: 0
            int result2 = "abc".compareTo("ABC");  // Output: 32

            System.out.println(result);            // -32
            System.out.println(result1);           // 0
            System.out.println(result2);           // 32

            // Negative → First String comes before second
            // 0        → Both Strings are equal
            // Positive → First String comes after second


            // ============================================================
            // 3) indexOf() → Find FIRST occurrence
            // ============================================================

            int idx = "Java".indexOf("a");          // First 'a' → index 1

            // J  a  v  a
            // 0  1  2  3

            System.out.println(idx);               // Output: 1


            // ============================================================
            // 4) lastIndexOf() → Find LAST occurrence
            // ============================================================

            int idx2 = "Java".lastIndexOf("a");     // Last 'a' → index 3

            System.out.println(idx2);               // Output: 3


            // ============================================================
            // 5) indexOf() with starting index
            // ============================================================

            String name = "nayana";

            // n  a  y  a  n  a
            // 0  1  2  3  4  5

            int index = name.indexOf("a", 3);       // Search 'a' from index 3
            System.out.println(index);              // Output: 3

            // There is NO betweenIndexOf() method.
            // indexOf("a", 3) → Search "a" from index 3 onward.


            // ============================================================
            // 6) Search within a specific range
            // ============================================================

            String name2 = "nayana";

            // n  a  y  a  n  a
            // 0  1  2  3  4  5

            String part = name2.substring(2, 5);    // "yan"

            // y  a  n
            // 0  1  2

            int index2 = part.indexOf("a");         // "a" index inside "yan" → 1

            System.out.println(index2);             // Output: 1
            System.out.println(index2 + 2);         // Output: 3

            // +2 because substring started at original index 2
            // Local index 1 + Starting index 2 = Original index 3


            // ============================================================
            // 7) isEmpty() → Check if String is empty
            // ============================================================

            boolean b = "".isEmpty();               // Empty String → true

            System.out.println(b);                  // Output: true


            // ============================================================
            // 8) String.join() → Join Strings
            // ============================================================

            String s11 = String.join("*", "Java", "Python");

            System.out.println(s11);                // Output: Java*Python


            // ============================================================
            // 9) replace() → Replace ALL occurrences
            // ============================================================

            String s12 = "Java".replace('a', 'o');

            System.out.println(s12);                // Output: Jovo


            // ============================================================
            // 10) Replace ONLY the LAST 'a'
            // ============================================================

            String s13 = "Java";

            // J  a  v  a
            // 0  1  2  3

            int lastA = s13.lastIndexOf('a');       // Last 'a' → index 3

            String beforeLastA = s13.substring(0, lastA);   // "Jav"
            String afterLastA = s13.substring(lastA + 1);  // ""

            s13 = beforeLastA + "o" + afterLastA;            // "Jav" + "o" + "" → "Javo"

            System.out.println(s13);                // Output: Javo


            // ============================================================
            // 11) startsWith() → Check beginning of String
            // ============================================================

            boolean b1 = "Java".startsWith("Ja");

            System.out.println(b1);                 // Output: true


            // ============================================================
            // 12) concat() → Join two Strings
            // ============================================================

            // "Java" + "Mava" → "JavaMava"

            String b2 = "Java".concat("Mava");

            System.out.println(b2);                 // Output: JavaMava


            // ============================================================
            // 13) Size vs Length
            // ============================================================

            // String      → length()
            // Array       → length
            // Collection  → size()


            // ============================================================
            // FINAL INTERVIEW REVISION
            // ============================================================

            // ==                  → Reference comparison
            // equals()            → Content comparison
            // equalsIgnoreCase()  → Content comparison ignoring case
            // charAt()            → Character at index
            // indexOf()           → FIRST occurrence
            // lastIndexOf()       → LAST occurrence
            // substring()         → Extract part of String
            // replace()           → Replace ALL matching characters
            // concat()            → Join Strings
            // startsWith()        → Check beginning
            // isEmpty()           → Check length == 0
            // length()            → String length
            // length              → Array length
            // size()              → Collection size

        }
}
