package ex_04_Operators;

public class Lab040_Relational_Operators {
    public static void main(String[] args) {
        // < Less Than
        // < =  -> Less than or equal to
        // > Greater
        // > = Greater or equal
        // == ->  Equal to (but checking)
        // != -> Not equal

        int a = 10;
        int b = 30;
        boolean c = a > b; // // 10> 30   // Relational operator always gives answer in boolean
        System.out.println(c);

        int age_mamitha = 33;
        int age_nikhil = 35;

        boolean result = age_nikhil >= age_mamitha;
        System.out.println(result);


    }
}
