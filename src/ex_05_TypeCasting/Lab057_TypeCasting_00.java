package ex_05_TypeCasting;

public class Lab057_TypeCasting_00 {
    public static void main(String[] args) {

        byte b  = 10;
        int a = b;  //  Valid syntax - Implicit - Casting Widening - JVM/ JAVA automatically do it
        float f = b;

        System.out.println(f);

        // boolean k = b; // Not Possible because data type is different

        int a1 = (int)b;  // Valid syntax - Explicit - Widening no need to mentioned int after = even if you mention int after = or not (not a problem)

    }

}
