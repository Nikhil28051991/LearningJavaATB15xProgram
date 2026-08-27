package ex_14_Strings;

public class Lab135_String_Immutable {
    public static void main(String[] args) {

        String name = "Nikhil";
        boolean result = name.contains("n");
        System.out.println(result);

        // String are immutable are in nature.

        name.toUpperCase();
        System.out.println(name);


    }
}
