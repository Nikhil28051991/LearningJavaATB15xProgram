package ex_02_Java_Basics;

public class Lab022_Numerics_Data_Types {
    public static void main(String[] args) {
        byte b = 10;
        short s = 10;
        int i = 10;
        char c = 'A';    // " Double quotes are not allowed in characters only single quote

       // char c1 = "A";   // Not allowed double quote and only one alphabet
        char c2 = '@';
        char c3 = '*';
        char c4 = '_';

        // int phone = 8788270435; // not allowed because overflow limit exceed

        //long phone =8788270435; // must have to mention L or small l after value

        long phone1 = 8788270435l; // For long, we must have to give the l suffix always

        long phone_2 = 8788270435L; // Capital L is also allowed

        float f = 3.14f; // for float f should be mentioned at the end of the value

        float f2 = 3.14F; // Capital F is also allowed

        double d = 3.12345678987; // For double we do not required d or D only for long and float we have to mention the l and f

        System.out.println(phone1);
        System.out.println(phone_2);
        System.out.println(c4);



    }
}
