package ex_12_DoWhile;

public class Lab126_Diff_While_DowhILE {
    public static void main(String[] args) {

        int a = 0;

        System.out.println("This is my while loop code");

        // While Loop
        while (a < 0) {
            System.out.println(a);
            a++;
        }

        System.out.println("This is my Do while Loop code");

        // Do While Loop
        do {
            System.out.println(a);
            a++;

        } while (a < 0);

    }
}

