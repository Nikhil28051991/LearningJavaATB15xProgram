package ex_06_Ternary_Operator;

public class Lab065_TO_INTERVIEW_Max_Two_TO {
    public static void main(String[] args) {

        int x = 10;
        int y = 20;

        int max = x > y ? x : y;

        int min = x > y ? y : x;

        int min1 = x < y ? x : y;

        System.out.println(max);
        System.out.println(min);
        System.out.println(min1);
    }
}
