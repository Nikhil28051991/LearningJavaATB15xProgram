package ex_05_TypeCasting;

public class Lab057_TypeCasting_0 {
    public static void main(String[] args) {

byte b = 10;                                                                // boolean k =b;  // It is not possible
int a = b; //  Valid syntax - Implicit - Casting Widening - JVM/ JAVA
float f = b;                                                             // Type Casting
                                                                       // Type Casting = converting one data type into another.
// boolean k = b; // Not Possible because data type is different      //There are two main types of type casting:
                                                                     //widening → Explicit, Implicit
System.out.println(f);                                              //narrowing → Explicit, Implicit
                                                                   //Widening Casting
                                                                  //
                                       //Widening casting, also known as implicit casting, occurs when converting a smaller data type to a larger one.
                                      //
                                     //This type of casting is performed automatically by the Java compiler and is considered safe because there's no risk of data loss.

        int a1 = (int)b; //  Valid syntax - Explicit - Widening no need to mentioned int
        System.out.println(a1);
    }
}
