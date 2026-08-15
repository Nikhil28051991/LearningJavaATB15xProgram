package ex_06_Ternary_Operator;

public class Lab064_Interview_Ready_Question {
    public static void main(String[] args) {
         // NTSBI
        int nikhil_age = 21;

        String result = nikhil_age > 18 ? (nikhil_age > 27 ? "You can drink": "You can't Drink") : "No you can't go to GOA";

        String result1 = nikhil_age > 21 ? (nikhil_age > 18 ? "You can drink": "You can't drink") : "No you can't go to GOA";

        String result2 = nikhil_age > 20 ? (nikhil_age > 18 ? "You can drink": "You can't drink") : "No you can't go to GOA";

        System.out.println(result);

        System.out.println(result1);

        System.out.println(result2);

    }
}
