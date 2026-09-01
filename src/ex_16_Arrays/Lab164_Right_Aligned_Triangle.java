package ex_16_Arrays;

public class Lab164_Right_Aligned_Triangle {
    public static void main(String[] args) {

        int n = 3;
        for (int i = 0; i < n; i++) {
            // 0,1,2

            for (int space = 0; space < n - i - 1; space++) {
                System.out.print(" ");
            }
            for (int star = 0; star <= i; star++) {
                System.out.print("*");
            }
            System.out.println();

        }
    }
}
