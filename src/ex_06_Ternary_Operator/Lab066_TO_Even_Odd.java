package ex_06_Ternary_Operator;

public class Lab066_TO_Even_Odd {
    public static void main(String[] args) {

        int a = 14;

        String result = (a%2 ==0) ? "even" : "odd";
        System.out.println(result);

        int b = 17;

        String result1 = (b%2 ==0) ? "even" : "odd";

        System.out.println(result1);
    }
}
