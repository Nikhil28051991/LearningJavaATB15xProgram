package ex_10_For_Loop;

public class Lab111_For_Loop_Break {
    public static void main(String[] args) {

        // My First Program
        for (int i = 0; i < 50; i++) {  // Times, From Where to Where 0 to 49, 50 times

            System.out.println(i); // It will print from 0 to 5 total 6 times because break is after the sout statement

            if (i == 5) {
                break;
            }
        }

        System.out.println("End");

        // Separator

        System.out.println("My Second Program");

        // My Second Program
        for (int i = 0; i < 50; i++) {  // 0 to 49, 50 times
            if (i == 5) {
                break;
            }

            System.out.println(i);  // It will print from 0 to 4 total 5 times because break is before the sout statement
        }

        System.out.println("End");

    }
}
