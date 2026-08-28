package ex_14_Strings;

public class LabStringExamples01 {
    public static void main(String[] args) {

        // ------------------------------------------------
        // 1) charAt()
        // ------------------------------------------------

        String s = "Java";

        char c = s.charAt(2);

        System.out.println(c);


        // ------------------------------------------------
        // 2) compareTo()
        // ------------------------------------------------

        int result = "ABC".compareTo("abc");
        int result1 = "abc".compareTo("abc");
        int result2 = "abc".compareTo("ABC");

        System.out.println(result);
        System.out.println(result1);
        System.out.println(result2);


        // ------------------------------------------------
        // 3) indexOf()
        // ------------------------------------------------

        int idx = "Java".indexOf("a"); // 1

        System.out.println(idx);


        // ------------------------------------------------
        // 4) lastIndexOf()
        // ------------------------------------------------

        int idx2 = "Java".lastIndexOf("a"); // 3

        System.out.println(idx2);


        // ------------------------------------------------
        // 5) isEmpty()
        // ------------------------------------------------

        boolean b = "".isEmpty(); // true

        System.out.println(b);


        // ------------------------------------------------
        // 6) String.join()
        // ------------------------------------------------

        String s11 = String.join("*", "Java", "Python");

        System.out.println(s11);


        // ------------------------------------------------
        // 7) replace()
        // ------------------------------------------------
        // replace() replaces ALL matching characters

        String s12 = "Java".replace('a', 'o');

        System.out.println(s12); // Jovo


        // ------------------------------------------------
        // 8) Find 'a' after 'y' in "nayana"
        // ------------------------------------------------

        String name = "nayana";

        // Index positions:
        // n  a  y  a  n  a
        // 0  1  2  3  4  5

        int index = name.indexOf("a", name.indexOf("y") + 1);

        System.out.println(index); // 3


        // ------------------------------------------------
        // 9) Replace ONLY the last 'a'
        // ------------------------------------------------

        String s13 = "Java";

        int lastA = s13.lastIndexOf('a');

        s13 = s13.substring(0, lastA)
                + "o"
                + s13.substring(lastA + 1);

        System.out.println(s13); // Javo


        // ------------------------------------------------
        // 10) startsWith()
        // ------------------------------------------------

        boolean b1 = "Java".startsWith("Ja");

        System.out.println(b1); // true


        // ------------------------------------------------
        // 11) concat()
        // ------------------------------------------------

        // Two String values are used here:
        //
        // 1) "Java"
        // 2) "Mava"
        //
        // concat() joins these two Strings:
        //
        // "Java" + "Mava"
        //      ↓
        // "JavaMava"

        String b2 = "Java".concat("Mava");

        System.out.println(b2); // JavaMava


        // ------------------------------------------------
        // 12) Size vs Length
        // ------------------------------------------------

        // Size does not exist in String.
        // Length exists in String.
        //
        // Length exists in Arrays as well as String.
        //
        // Size exists in Collection Framework.

    }
}
