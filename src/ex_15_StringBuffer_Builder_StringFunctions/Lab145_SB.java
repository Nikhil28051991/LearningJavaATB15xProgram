package ex_15_StringBuffer_Builder_StringFunctions;

public class Lab145_SB {
    public static void main(String[] args) {

        StringBuffer stringBuffer = new StringBuffer("Nikhil");
        stringBuffer.append("Sonawane");
        System.out.println(stringBuffer); // In this only one String Created


        String s1 = "Nikhil";
        s1 = s1+ "Sonawane";
        System.out.println(s1); // In this two Strings will be created Nikhil and Nikhil Sonawane,  therefore we generally use StringBuffer

    }
}
