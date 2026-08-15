package ex_06_Ternary_Operator;

public class Lab067_To_Three_Max1 {
    public static void main(String[] args) {
        //NTSBI
        int n1 = 2;
        int n2 = 5;
        int n3 = -11;

        System.out.println(n1);
        System.out.println(n2);
        System.out.println(n3);

        System.out.println("MAX OUT OF THREE");

        int max = n1 > n2 ? n1 : n2;
        max = max > n3 ? max : n3;

        System.out.println("Maximum number = " + max);
    }
}
