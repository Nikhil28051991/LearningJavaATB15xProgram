package ex_05_TypeCasting;

public class Lab060_TypeCasting {
    public static void main(String[] args) {

        long phone = 8788270435l;
//        short s = phone; // Narrowing - implicit.          It is not allowed

        short s1 = (short) phone; // Narrowing - Explicit.   It is allowed (short) phone

        System.out.println(s1);
    }
}
