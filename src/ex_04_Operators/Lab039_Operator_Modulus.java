package ex_04_Operators;

public class Lab039_Operator_Modulus {
    public static void main(String[] args) {
        int a = 20;
        int b = 10;
        System.out.println(a%b);     // % is nothing but modulus or Remainder

        //        10 | 20 |  2 - quotient             (20/10) = 2  - quotient  and  0 - Remainder
//                  |   20 |
//                  -------
//                      0 - Remainder
//                  ----

        System.out.println(13%7);
        //        // 7 | 13 | 1 - Q                   (13/7) = 1 - quotient
//        //       7                                  -7
//        // R ------ 6                                        6 - Remainder // It will only return Remainder value in console
//
////         11%2 -> 1 , 13%2 -> 1
////                10%2 -> 0
////                12%2 -> 0
////            Number % 2 -> R-> 1 -> odd , 0 -> even
        // num%2 == 0 - even, else 1 odd

    }
}
