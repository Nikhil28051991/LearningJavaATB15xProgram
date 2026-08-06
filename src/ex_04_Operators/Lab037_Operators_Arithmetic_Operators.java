package ex_04_Operators;

public class Lab037_Operators_Arithmetic_Operators {
    public static void main(String[] args) {

        // Arithmetic Operators                         Basically Three main Types of Operator
        //        + (Addition)                           Unary
        //       - (Subtraction)                         Binary
        //       * (Multiplication)                      Ternary
        //       / (Division)
        //       % (Modulus) | Modulus or Remainder

        int a = 20;
        int b = 3;
        System.out.println(a+b);   // Here a and b are two Operant and + sign is Operator
        System.out.println(a-b);
        System.out.println(a*b);
        System.out.println(a/b);
        float c = 3.0f;            // Any whole number divided by float gives answer in float always
        System.out.println(a/c);
        System.out.println("a+b"); // If user put variables in double quotes " " then it act as a String It will not perform any Mathematical Operation
        System.out.println(a+b);

        /*
==================== Java Operators ====================

No.  Type                  Operators                           Example
---------------------------------------------------------------------------
1.   Arithmetic            +  -  *  /  %                      a + b, a % b
2.   Unary                 +  -  ++  --  !  ~                ++a, --b, !flag
3.   Assignment            = += -= *= /= %= &= |= ^=         a += 5
                            <<= >>= >>>=
4.   Relational            == != > < >= <=                   a > b
5.   Logical               && || !                           a>b && b<c
6.   Bitwise               & | ^ ~                           a & b
7.   Shift                 << >> >>>                         a << 2
8.   Ternary               ? :                               (a>b)?a:b

=========================================================
*/

    }
}
