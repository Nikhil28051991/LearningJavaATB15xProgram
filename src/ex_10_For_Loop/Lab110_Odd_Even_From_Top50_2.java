package ex_10_For_Loop;

public class Lab110_Odd_Even_From_Top50_2 {
    public static void main(String[] args) {
        // My First Program
        // Even numbers from 1 to 50

        for (int i = 1; i <= 50; i++) {

            if (i % 2 == 0) {
                System.out.println("Even => " + i);
            }
        }

        System.out.println("My Second Program");

        // My Second Program
        // Odd numbers from 1 to 50

        for (int i = 1; i <= 50; i++) {

            if (i % 2 != 0) {
                System.out.println("Odd => " + i);
            }
        }
    }
}
