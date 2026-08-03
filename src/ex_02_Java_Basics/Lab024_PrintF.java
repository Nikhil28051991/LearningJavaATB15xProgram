package ex_02_Java_Basics;

public class Lab024_PrintF {
    public static void main(String[] args) {

        System.out.println("It will add a new line");

        System.out.print("It will not add a new line");

        System.out.printf("This is a normal text");
        System.out.printf("This is a normal text");

        System.out.println("It will add a new line");

        int a = 10;
        System.out.println(a);
        System.out.print("Value of a is -> " + a);
        System.out.println("Value of a is -> " + a);

        System.out.printf("Value Of a Is -> %d", a);

        // %d -> int, byte, short, long - data type
        // %s -> String
        // %f -> float,double
        // %b -> boolean

        int aa = 100;
        int bb =121;

        System.out.printf("When you mul aa*bb result is = %d*%d", aa, bb); // this is just use for formatting the output
        System.out.printf("Formatting the aa = %d and bb =%d ", aa ,bb);

        System.out.println();
        int table =9;
        System.out.printf("%d*1=%d", table, table*1);


    }
}
