package ex_05_TypeCasting;

public class Lab061_TypCasting_Used {
    public static void main(String[] args) {

        int course = 100;
        float NSRT_GST = 18.45f;
////        int total = course+ NSRT_GST; // Narrowing - Implicit.

       int total = course+ (int)NSRT_GST; // Narrowing - Explicit. It is allowed, but we are loosing 0.45 because float is bigger data type

       System.out.println(total);

        float total2 = course+ NSRT_GST; // widening  - Implicit
        float total3 = (float) course+ NSRT_GST; // widening  - Explicit This is allowed but not required
        System.out.println(total2);


    }
}
