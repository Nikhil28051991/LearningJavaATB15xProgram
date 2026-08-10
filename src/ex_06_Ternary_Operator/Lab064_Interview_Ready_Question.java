package ex_06_Ternary_Operator;

public class Lab064_Interview_Ready_Question {
    public static void main(String[] args) {

        int nikhil_age = 21;

        String result = nikhil_age > 22 ? (nikhil_age > 25 ? "You can drink": "You can't") : "No you can't go to GOA";

        String result1 = nikhil_age > 18 ? (nikhil_age > 25 ? "You can drink": "You can't drink") : "No you can't go to GOA";

        System.out.println(result);

        System.out.println(result1);

    }
}
