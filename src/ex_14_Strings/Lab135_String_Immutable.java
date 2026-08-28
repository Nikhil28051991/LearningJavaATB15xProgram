package ex_14_Strings;

public class Lab135_String_Immutable {
    public static void main(String[] args) {

        String name = "Nikhil";
        boolean result = name.contains("n");
        System.out.println(result);

        // String are immutable are in nature.

        name.toUpperCase();         // This will create a new String NIKHIL but not assign to name

        System.out.println(name);

 //name = name.toUpperCase(); // This will assign the value and give the output NIKHIL, and also it will not give the output Nikhil even though above line is uncommented
                              // means name.toUpperCase(); and name = name.toUpperCase(); both are active still it gives NIKHIL output not Nikhil

        System.out.println(name);   // Therefore out put will be Nikhil only not NIKHIL


    }
}
