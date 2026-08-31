package ex_16_Arrays;

import java.util.Scanner;

public class Lab154_Array_User_Inputs {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the size of the array");
        int size=  scanner.nextInt();

        int[] numbers_marks =  new int[size];

        for (int i = 0; i < numbers_marks.length; i++) {
            System.out.println("Enter the marks");
            numbers_marks[i] = scanner.nextInt();
        }

        System.out.println(" ==== ");


        for (int i = 0; i < numbers_marks.length; i++) {
            System.out.println(numbers_marks[i]);
        }

        System.out.println(" ==== ");


        for(int a: numbers_marks){   // For each loop generally used for printing purpose
            System.out.println(a);

        }
    }
}
