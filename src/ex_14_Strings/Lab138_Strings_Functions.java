package ex_14_Strings;

public class Lab138_Strings_Functions {
    public static void main(String[] args) {

        char c = 'A';
        System.out.println(c);

        String s1 = "ABCD";
        System.out.println(s1);
        System.out.println(s1.length());
        System.out.println(s1.toLowerCase());
        System.out.println(s1.toUpperCase());   // It will not create new String because ABCD is already Present s1 = "ABCD"
        System.out.println(s1.concat("E"));
        System.out.println(s1.concat("1"));

        int num = 28;

        // Add int 28 to String
        System.out.println(s1 + num);

        // Using concat()
        System.out.println(s1.concat(String.valueOf(num)));

    }
}
