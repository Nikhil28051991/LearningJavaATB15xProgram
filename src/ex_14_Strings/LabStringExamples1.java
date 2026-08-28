package ex_14_Strings;

public class LabStringExamples1 {
    public static void main(String[] args) {

                String s= "Java";
        char c = s.charAt(2);
        System.out.println(c);

        int result = "ABC".compareTo("abc");
        int result1 = "abc".compareTo("abc");
        int result2 = "abc".compareTo("ABC");
        System.out.println(result);
        System.out.println(result1);
        System.out.println(result2);

        int idx = "Java".indexOf("a"); // 1
        System.out.println(idx);

        int idx2 = "Java".lastIndexOf("a"); // 3
        System.out.println(idx2);

        boolean b = "".isEmpty(); // true
        System.out.println(b);

        String s11 = String.join("*", "Java", "Python");
        System.out.println(s11);

        String s12 = "Java".replace('a', 'o'); // "Jovo"
        System.out.println(s12);

        boolean b1 = "Java".startsWith("Ja"); // true
        System.out.println(b1);

        String b2 = "Java".concat("Mava"); // true
        System.out.println(b2);

        // Size does not exist in String Length Exist.  (Length Exist in Arrays as well as String)
        // Size Exist in Collection Framework

    }
}
