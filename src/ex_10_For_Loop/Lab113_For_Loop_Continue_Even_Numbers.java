package ex_10_For_Loop;

public class Lab113_For_Loop_Continue_Even_Numbers {
    public static void main(String[] args) {
        // First Program
        for (int i = 0; i <= 50; i++) {     // 0 to 50, Times - 51
            if (i % 2 == 0) {               // continue will skip even numbers
                continue;
            }
            System.out.println(i);          // Prints odd numbers
        }

        System.out.println("My Second Program");

        // Second Program
        for (int i = 0; i <= 50; i++) {     // 0 to 50, Times - 51
            if (i % 2 != 0) {               // continue will skip odd numbers
                continue;
            }
            System.out.println(i);         // Prints even numbers
        }
    }
}