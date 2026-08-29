package ex_15_StringBuffer_Builder_StringFunctions;

public class Lab144_StringBuilder_Vs_Buffer {
    public static void main(String[] args) {

        // String - 90%
        String s0 = "Nikhil";
        String s1 = new String("Nikhil");

        // less than <10% used.
        StringBuffer stringBuffer = new StringBuffer("Nikhil");
        StringBuilder stringBuilder = new StringBuilder("Nikhil");

        System.out.println(stringBuffer.reverse());
        System.out.println(stringBuilder.reverse());

    }
}
