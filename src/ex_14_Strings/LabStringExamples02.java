package ex_14_Strings;

public class LabStringExamples02 {

    public static void main(String[] args) {

        // 1) charAt() - Get character using index
        String s = "Java";
        // J  a  v  a
        // 0  1  2  3
        char c = s.charAt(2);
        System.out.println(c); // v


        // 2) compareTo() - Compare two Strings
        int result = "ABC".compareTo("abc");
        int result1 = "abc".compareTo("abc");
        int result2 = "abc".compareTo("ABC");

        System.out.println(result);
        System.out.println(result1);
        System.out.println(result2);


        // 3) indexOf() - Find FIRST occurrence
        int idx = "Java".indexOf("a");
        // J  a  v  a
        // 0  1  2  3
        System.out.println(idx); // 1


        // 4) lastIndexOf() - Find LAST occurrence
        int idx2 = "Java".lastIndexOf("a");
        System.out.println(idx2); // 3


        // 5) indexOf() with starting index
        String name = "nayana";
        // n  a  y  a  n  a
        // 0  1  2  3  4  5

        int index = name.indexOf("a", 3);
        // Search 'a' starting FROM index 3
        System.out.println(index); // 3

        // There is NO betweenIndexOf() method in Java.
        // indexOf("a", 3) means: search 'a' from index 3 onward.


        // 6) Search within a specific range

        String name2 = "nayana";

           // n  a  y  a  n  a
          // 0  1  2  3  4  5

        String part = name2.substring(2, 5);  // "yan"

           // y  a  n
          // 0  1  2

        int index2 = part.indexOf("a");  // 1

        System.out.println(index2);      // 1 → index inside "yan"
        System.out.println(index2 + 2);  // 3 → index in original "nayana"

        // Why + 2?
        //Because the substring started at index 2 of the original string.


        // 7) isEmpty() - Check if String is empty
        boolean b = "".isEmpty();
        System.out.println(b); // true


        // 8) String.join() - Join Strings
        String s11 = String.join("*", "Java", "Python");
        System.out.println(s11); // Java*Python


        // 9) replace() - Replace ALL occurrences
        String s12 = "Java".replace('a', 'o');
        System.out.println(s12); // Jovo


        // 10) Replace ONLY the LAST 'a'
        String s13 = "Java";
        // J  a  v  a
        // 0  1  2  3

        int lastA = s13.lastIndexOf('a'); // 3

        // substring(0, 3) = "Jav"
        String beforeLastA = s13.substring(0, lastA);

        // substring(4) = "" because nothing is after index 3
        String afterLastA = s13.substring(lastA + 1);

        // "Jav" + "o" + "" = "Javo"
        s13 = beforeLastA + "o" + afterLastA;

        System.out.println(s13); // Javo


        // 11) startsWith() - Check beginning of String
        boolean b1 = "Java".startsWith("Ja");
        System.out.println(b1); // true


        // 12) concat() - Join two Strings

        // TWO String literals:
        // 1) "Java"  → String Pool
        // 2) "Mava"  → String Pool
        //
        // concat() joins them:
        // "Java" + "Mava" = "JavaMava"
        //
        // "JavaMava" is the NEW String result created by concat().
        // b2 refers to this result.

        String b2 = "Java".concat("Mava");

        System.out.println(b2); // JavaMava


        // 13) Size vs Length
        // String  → length()
        // Array   → length
        // Collection → size()


    }
}
