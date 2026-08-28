package ex_14_Strings;

public class Lab137_String_Imm {
    public static void main(String[] args) {

        String s1 = "hello";
        s1  = s1.concat("world");
        System.out.println(s1); // It will store world in s1 along with hello kept as it is so the output will be helloworld
    }
}
