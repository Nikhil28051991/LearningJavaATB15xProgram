package ex_02_Java_Basics;

public class Lab025_Constants {
    public static void main(String[] args) {
        int a = 10;
        a = 20;
        System.out.println(a);

        float PI = 3.14f;
        PI = 3.122f;
        System.out.println(PI);

       final float PI1 = 3.14f;
        // PI1 = 3.122f; // Not allowed because already declared final
        System.out.println(PI1);
    }
}
