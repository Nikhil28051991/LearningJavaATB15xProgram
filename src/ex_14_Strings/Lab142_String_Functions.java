package ex_14_Strings;

public class Lab142_String_Functions {
    public static void main(String[] args) {

        //        String s1="HELLO";
       //  String s2= new String("world");

        String str1 = "Hello";
        String str2 = "Hello";
        String str3 = new String("Hello");
        System.out.println(str1 == str2);     // Both are Present in String Constant pool (SCP)
        System.out.println(str1 == str3);    // Because s1 is Present in String Constant Pool and s3 is Present in Object Area
        System.out.println(str1.equals(str3));  // There content is equal
    }
}
